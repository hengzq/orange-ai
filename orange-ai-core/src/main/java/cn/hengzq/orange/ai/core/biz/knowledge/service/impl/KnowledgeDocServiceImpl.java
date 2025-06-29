package cn.hengzq.orange.ai.core.biz.knowledge.service.impl;

import cn.hengzq.orange.ai.common.biz.knowledge.constant.*;
import cn.hengzq.orange.ai.common.biz.knowledge.event.DocRefreshEvent;
import cn.hengzq.orange.ai.common.biz.knowledge.event.DocRefreshParam;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.*;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.*;
import cn.hengzq.orange.ai.common.biz.model.constant.AIModelErrorCode;
import cn.hengzq.orange.ai.core.biz.knowledge.converter.KnowledgeDocConverter;
import cn.hengzq.orange.ai.core.biz.knowledge.converter.KnowledgeDocSliceConverter;
import cn.hengzq.orange.ai.core.biz.knowledge.entity.KnowledgeDocEntity;
import cn.hengzq.orange.ai.core.biz.knowledge.entity.KnowledgeDocSliceEntity;
import cn.hengzq.orange.ai.core.biz.knowledge.mapper.KnowledgeDocMapper;
import cn.hengzq.orange.ai.core.biz.knowledge.mapper.KnowledgeDocSliceMapper;
import cn.hengzq.orange.ai.core.biz.knowledge.service.*;
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
public class KnowledgeDocServiceImpl implements KnowledgeDocService {

    private final KnowledgeBaseService knowledgeBaseService;

    private final KnowledgeDocMapper knowledgeDocMapper;

    private final KnowledgeDocSliceService knowledgeDocSliceService;

    private final KnowledgeDocSliceMapper knowledgeDocSliceMapper;

    private final FileSliceStrategyFactory fileSliceStrategyFactory;

    private final ApplicationContext applicationContext;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteById(String id) {
        KnowledgeDocEntity entity = knowledgeDocMapper.selectById(id);
        if (Objects.isNull(entity)) {
            return Boolean.TRUE;
        }
        // 删除切片
        if (!knowledgeDocSliceService.deleteByDocId(id)) {
            log.error("delete doc[{}] slice is failed.", id);
            throw new ServiceException(KnowledgeErrorCode.DOC_DELETE_FAILED);
        }
        knowledgeDocMapper.deleteById(id);
        return Boolean.TRUE;
    }


    @Override
    public KnowledgeDocVO getById(String id) {
        KnowledgeDocEntity entity = knowledgeDocMapper.selectById(id);
        return KnowledgeDocConverter.INSTANCE.toVO(entity);
    }

    @Override
    public List<KnowledgeDocVO> list(KnowledgeDocListParam param) {
        List<KnowledgeDocEntity> entityList = knowledgeDocMapper.selectList(CommonWrappers.<KnowledgeDocEntity>lambdaQuery()
                .likeIfPresent(KnowledgeDocEntity::getFileName, param.getFileName())
                .eqIfPresent(KnowledgeDocEntity::getBaseId, param.getBaseId())
                .in(CollUtil.isNotEmpty(param.getIds()), KnowledgeDocEntity::getId, param.getIds())
                .orderByDesc(KnowledgeDocEntity::getCreatedAt)
        );
        return KnowledgeDocConverter.INSTANCE.toListVO(entityList);
    }

    @Override
    public PageDTO<KnowledgeDocVO> page(KnowledgeDocumentPageParam param) {
        PageDTO<KnowledgeDocEntity> page = knowledgeDocMapper.selectPage(param, CommonWrappers.<KnowledgeDocEntity>lambdaQuery()
                .likeIfPresent(KnowledgeDocEntity::getFileName, param.getFileName())
                .eq(KnowledgeDocEntity::getBaseId, param.getBaseId())
                .orderByDesc(KnowledgeDocEntity::getCreatedAt)
        );
        return KnowledgeDocConverter.INSTANCE.toPage(page);
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
        KnowledgeBaseVO base = knowledgeBaseService.getById(param.getBaseId());
        Assert.nonNull(base, KnowledgeErrorCode.KNOWLEDGE_BASE_DOES_NOT_EXIST);

        // 知识库关联文档
        KnowledgeDocEntity documentEntity = new KnowledgeDocEntity();
        documentEntity.setBaseId(param.getBaseId());
        documentEntity.setFileName(param.getFileName());
        String docId = knowledgeDocMapper.insertOne(documentEntity);

        // 文档内容切片
        KnowledgeDocSliceEntity slice = new KnowledgeDocSliceEntity();
        slice.setBaseId(param.getBaseId());
        slice.setDocId(docId);
        slice.setContent(param.getContent());
        slice.setEmbStatus(SliceEmbStatus.PENDING);
        knowledgeDocSliceMapper.insert(slice);

        // 发送新增文档事件
        applicationContext.publishEvent(new DocRefreshEvent(DocRefreshParam.builder().docIds(List.of(docId)).type(DocRefreshType.ADD).build()));
        return docId;
    }

    @Override
    public List<KnowledgeDocSplitVO> split(KnowledgeDocSplitParam param) {
        List<KnowledgeDocSplitVO> list = new ArrayList<>();
        for (FileInfo info : param.getFileList()) {
            KnowledgeDocSplitVO vo = new KnowledgeDocSplitVO();
            vo.setFileInfo(info);
            FileSliceStrategy strategy = fileSliceStrategyFactory.getFileSliceStrategy(FileTypeEnum.getByName(FileUtil.extName(info.getFileName())));
            list.add(KnowledgeDocSplitVO.builder()
                    .fileInfo(info)
                    .sliceList(strategy.split(info.getFilePath()))
                    .build());
        }
        return list;
    }

    @Override
    public Boolean batchAddDocumentAndSlice(AddDocumentAndSliceParam param) {
        // 查询知识库
        KnowledgeBaseVO base = knowledgeBaseService.getById(param.getBaseId());
        Assert.nonNull(base, KnowledgeErrorCode.KNOWLEDGE_BASE_DOES_NOT_EXIST);
        List<String> docIds = new ArrayList<>();
        for (AddDocumentAndSliceParam.DocumentInfo document : param.getDocumentList()) {
            KnowledgeDocEntity documentEntity = KnowledgeDocConverter.INSTANCE.toEntity(param.getBaseId(), document);
            documentEntity.setFileStatus(FileStatusEnum.EMB_PENDING);
            String docId = knowledgeDocMapper.insertOne(documentEntity);
            if (CollUtil.isNotEmpty(document.getSliceList())) {
                List<KnowledgeDocSliceEntity> sliceEntityList = KnowledgeDocSliceConverter.INSTANCE.toEntityList(param.getBaseId(), docId, document.getSliceList());
                sliceEntityList.forEach(item -> item.setEmbStatus(SliceEmbStatus.PENDING));
                knowledgeDocSliceMapper.insert(sliceEntityList);
            }
            docIds.add(docId);
        }
        // 发送新增文档事件
        applicationContext.publishEvent(new DocRefreshEvent(DocRefreshParam.builder().docIds(docIds).type(DocRefreshType.ADD).build()));
        return true;
    }

    @Override
    public void updateFileStatusById(String id, FileStatusEnum fileStatus) {
        KnowledgeDocEntity entity = knowledgeDocMapper.selectById(id);
        Assert.nonNull(entity, AIModelErrorCode.GLOBAL_DATA_NOT_EXIST);
        entity.setFileStatus(fileStatus);
        knowledgeDocMapper.updateById(entity);
    }

    @Override
    public Map<String, KnowledgeDocVO> mapKnowledgeDocByIds(List<String> docIds) {
        if (CollUtil.isEmpty(docIds)) {
            return Map.of();
        }
        List<KnowledgeDocEntity> entityList = knowledgeDocMapper.selectBatchIds(docIds);

        List<KnowledgeDocVO> docList = KnowledgeDocConverter.INSTANCE.toListVO(entityList);
        if (CollUtil.isEmpty(docList)) {
            return Map.of();
        }
        return CollUtils.convertMap(docList, KnowledgeDocVO::getId, param -> param);
    }


}
