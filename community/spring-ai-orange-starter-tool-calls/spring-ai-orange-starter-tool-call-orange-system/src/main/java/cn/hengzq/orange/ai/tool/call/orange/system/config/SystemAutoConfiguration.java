package cn.hengzq.orange.ai.tool.call.orange.system.config;

import cn.hengzq.orange.ai.tool.call.orange.system.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@Slf4j
@AutoConfiguration
public class SystemAutoConfiguration {

    @Bean
    public RoleService roleService() {
        return new RoleService();
    }

}
