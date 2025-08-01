package cn.hengzq.orange.ai.core.biz.knowledge.service.impl;

import cn.hengzq.orange.ai.common.biz.knowledge.constant.DocRefreshType;
import cn.hengzq.orange.ai.common.biz.knowledge.constant.DocStatusEnum;
import cn.hengzq.orange.ai.common.biz.knowledge.constant.FileTypeEnum;
import cn.hengzq.orange.ai.common.biz.knowledge.constant.KnowledgeErrorCode;
import cn.hengzq.orange.ai.common.biz.knowledge.event.DocRefreshEvent;
import cn.hengzq.orange.ai.common.biz.knowledge.event.DocRefreshParam;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.*;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.*;
import cn.hengzq.orange.ai.common.biz.model.constant.AIModelErrorCode;
import cn.hengzq.orange.ai.core.biz.knowledge.converter.ChunkConverter;
import cn.hengzq.orange.ai.core.biz.knowledge.converter.DocConverter;
import cn.hengzq.orange.ai.core.biz.knowledge.entity.BaseEntity;
import cn.hengzq.orange.ai.core.biz.knowledge.entity.DocEntity;
import cn.hengzq.orange.ai.core.biz.knowledge.mapper.BaseMapper;
import cn.hengzq.orange.ai.core.biz.knowledge.mapper.DocMapper;
import cn.hengzq.orange.ai.core.biz.knowledge.service.ChunkService;
import cn.hengzq.orange.ai.core.biz.knowledge.service.DocService;
import cn.hengzq.orange.ai.core.biz.knowledge.service.FileSliceStrategy;
import cn.hengzq.orange.ai.core.biz.knowledge.service.FileSliceStrategyFactory;
import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.common.exception.ServiceException;
import cn.hengzq.orange.common.util.Assert;
import cn.hengzq.orange.common.util.CollUtils;
import cn.hengzq.orange.mybatis.query.CommonWrappers;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import com.vladsch.flexmark.html2md.converter.FlexmarkHtmlConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * @author hengzq
 */
@Slf4j
@Service
@AllArgsConstructor
public class DocServiceImpl implements DocService {

    private final BaseMapper baseMapper;

    private final DocMapper docMapper;

    private final ChunkService chunkService;

    private final FileSliceStrategyFactory fileSliceStrategyFactory;

    private final ApplicationContext applicationContext;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteById(String id) {
        DocEntity entity = docMapper.selectById(id);
        if (Objects.isNull(entity)) {
            return Boolean.TRUE;
        }
        // 删除切片
        if (!chunkService.deleteByDocId(id)) {
            log.error("delete doc[{}] slice is failed.", id);
            throw new ServiceException(KnowledgeErrorCode.DOC_DELETE_FAILED);
        }
        docMapper.deleteById(id);
        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteByBaseId(String baseId) {
        List<DocEntity> docList = docMapper.selectByBaseId(baseId);
        if (CollUtil.isEmpty(docList)) {
            return Boolean.TRUE;
        }
        List<String> docIds = CollUtils.convertList(docList, DocEntity::getId);
        int delete = docMapper.deleteByIds(docIds);
        return delete > 0;
    }


    @Override
    public DocVO getById(String id) {
        DocEntity entity = docMapper.selectById(id);
        return DocConverter.INSTANCE.toVO(entity);
    }

    @Override
    public List<DocVO> list(KnowledgeDocListParam param) {
        List<DocEntity> entityList = docMapper.selectList(CommonWrappers.<DocEntity>lambdaQuery()
                .likeIfPresent(DocEntity::getFileName, param.getFileName())
                .eqIfPresent(DocEntity::getBaseId, param.getBaseId())
                .in(CollUtil.isNotEmpty(param.getIds()), DocEntity::getId, param.getIds())
                .orderByDesc(DocEntity::getCreatedAt)
        );
        return DocConverter.INSTANCE.toListVO(entityList);
    }

    @Override
    public PageDTO<DocVO> page(KnowledgeDocumentPageParam param) {
        PageDTO<DocEntity> page = docMapper.selectPage(param, CommonWrappers.<DocEntity>lambdaQuery()
                .likeIfPresent(DocEntity::getFileName, param.getFileName())
                .eq(DocEntity::getBaseId, param.getBaseId())
                .orderByDesc(DocEntity::getCreatedAt)
        );
        return DocConverter.INSTANCE.toPage(page);
    }

    @Override
    public WebContentVO getMarkdownFromUrl(UrlParam param) {
        try {
            // 获取网页内容
            Document document = Jsoup.connect(param.getUrl()).get();
            FlexmarkHtmlConverter converter = FlexmarkHtmlConverter.builder().build();
            return WebContentVO.builder()
                    .title(document.title())
                    .content(converter.convert(document.body().html()))
                    .build();
        } catch (Exception e) {
            log.error("get content is error. msg:{}", e.getMessage(), e);
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addTextToKnowledge(AddTextToKnowledgeParam param) {
        // 查询知识库
//        KnowledgeBaseVO base = knowledgeBaseService.getById(param.getBaseId());
//        Assert.nonNull(base, KnowledgeErrorCode.KNOWLEDGE_BASE_DOES_NOT_EXIST);

        // 知识库关联文档
        DocEntity documentEntity = new DocEntity();
        documentEntity.setBaseId(param.getBaseId());
        documentEntity.setFileName(param.getFileName());
        String docId = docMapper.insertOne(documentEntity);

        // 文档内容切片
//        ChunkEntity slice = new ChunkEntity();
//        slice.setBaseId(param.getBaseId());
//        slice.setDocId(docId);
//        slice.setText(param.getContent());
//        slice.setEmbStatus(SliceEmbStatus.PENDING);
//        chunkMapper.insert(slice);

        // 发送新增文档事件
        applicationContext.publishEvent(new DocRefreshEvent(DocRefreshParam.builder().docIds(List.of(docId)).type(DocRefreshType.ADD).build()));
        return docId;
    }

    @Override
    public List<DocSplitVO> split(DocSplitParam param) {
        List<DocSplitVO> list = new ArrayList<>();
        for (FileInfo info : param.getFileList()) {
            DocSplitVO vo = new DocSplitVO();
            vo.setFileInfo(info);
            FileSliceStrategy strategy = fileSliceStrategyFactory.getFileSliceStrategy(FileTypeEnum.getByName(FileUtil.extName(info.getFileName())));
            List<org.springframework.ai.document.Document> documents = strategy.read(info.getFilePath());
            TokenTextSplitter splitter = TokenTextSplitter.builder()
                    .withChunkSize(param.getChunkSize())
                    .build();
            list.add(DocSplitVO.builder()
                    .fileInfo(info)
                    .chunks(ChunkConverter.INSTANCE.documentListToListVO(splitter.split(documents)))
                    .build());
        }
        return list;
    }

    @Override
    public Boolean batchAdd(AddDocAndChunkParam param) {
        // 查询知识库
        BaseEntity base = baseMapper.selectById(param.getBaseId());
        Assert.nonNull(base, KnowledgeErrorCode.KNOWLEDGE_BASE_DOES_NOT_EXIST);

        List<String> docIds = new ArrayList<>();
        for (AddDocAndChunkParam.DocumentInfo document : param.getDocs()) {
            DocEntity documentEntity = DocConverter.INSTANCE.toEntity(param.getBaseId(), document);
            documentEntity.setFileStatus(DocStatusEnum.EMB_PENDING);
            String docId = docMapper.insertOne(documentEntity);

            if (CollUtil.isNotEmpty(document.getChunks())) {
                List<ChunkVO> chunks = document.getChunks().stream().map(item -> {
                    item.setBaseId(base.getId());
                    item.setDocId(docId);
                    return item;
                }).toList();
                chunkService.batchAdd(chunks);
            }
            docIds.add(docId);
        }
        // 发送新增文档事件
        applicationContext.publishEvent(new DocRefreshEvent(DocRefreshParam.builder().docIds(docIds).type(DocRefreshType.ADD).build()));
        return true;
    }

    @Override
    public void updateFileStatusById(String id, DocStatusEnum fileStatus) {
        DocEntity entity = docMapper.selectById(id);
        Assert.nonNull(entity, AIModelErrorCode.GLOBAL_DATA_NOT_EXIST);
        entity.setFileStatus(fileStatus);
        docMapper.updateById(entity);
    }

    @Override
    public Map<String, DocVO> mapKnowledgeDocByIds(List<String> docIds) {
        if (CollUtil.isEmpty(docIds)) {
            return Map.of();
        }
        List<DocEntity> entityList = docMapper.selectBatchIds(docIds);

        List<DocVO> docList = DocConverter.INSTANCE.toListVO(entityList);
        if (CollUtil.isEmpty(docList)) {
            return Map.of();
        }
        return CollUtils.convertMap(docList, DocVO::getId, param -> param);
    }


}
