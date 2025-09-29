package cn.hengzq.orange.ai.core.biz.app.mapper;

import cn.hengzq.orange.ai.common.biz.app.dto.AppListResponse;
import cn.hengzq.orange.ai.common.biz.app.dto.request.AppPageRequest;
import cn.hengzq.orange.ai.core.biz.app.entity.AppEntity;
import cn.hengzq.orange.mybatis.mapper.CommonMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author hengzq
 */
@Repository
public interface AppMapper extends CommonMapper<AppEntity> {

    IPage<AppListResponse> selectAppPage(Page<Object> page,  @Param("request") AppPageRequest request);
}
