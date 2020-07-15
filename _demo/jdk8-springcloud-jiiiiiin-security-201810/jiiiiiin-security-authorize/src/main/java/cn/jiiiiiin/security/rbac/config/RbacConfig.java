package cn.jiiiiiin.security.rbac.config;

import cn.jiiiiiin.security.rbac.component.service.RbacService;
import cn.jiiiiiin.security.rbac.component.service.impl.RbacServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RbacConfig {

    @Bean
    @ConditionalOnMissingBean(name = "rbacService")
    public RbacService rbacService() {
        return new RbacServiceImpl();
    }
}
