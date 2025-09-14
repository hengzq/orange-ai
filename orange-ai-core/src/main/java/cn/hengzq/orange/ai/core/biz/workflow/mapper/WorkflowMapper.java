package cn.hengzq.orange.ai.core.biz.workflow.mapper;

import cn.hengzq.orange.ai.common.biz.app.vo.param.WorkflowPageRequest;
import cn.hengzq.orange.ai.common.biz.workflow.dto.WorkflowListResponse;
import cn.hengzq.orange.ai.core.biz.workflow.entity.WorkflowEntity;
import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.mybatis.mapper.CommonMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author hengzq
 */
@Repository
public interface WorkflowMapper extends CommonMapper<WorkflowEntity> {

    IPage<WorkflowListResponse> selectWorkflowPage(Page<WorkflowListResponse> page, @Param("request") WorkflowPageRequest request);
}
