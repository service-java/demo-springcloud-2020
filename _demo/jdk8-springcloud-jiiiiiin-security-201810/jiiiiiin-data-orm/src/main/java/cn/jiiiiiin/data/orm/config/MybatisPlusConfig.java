package cn.jiiiiiin.data.orm.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.plugins.SqlExplainInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author jiiiiiin
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 性能分析拦截器，不建议生产使用
     */
    @Bean
    @Profile({"dev","test"})
    public PerformanceInterceptor performanceInterceptor(){
        return new PerformanceInterceptor().setFormat(true).setMaxTime(500);
    }

    @Bean
    public SqlExplainInterceptor sqlExplainInterceptor(){
        //启用执行分析插件
        SqlExplainInterceptor sqlExplainInterceptor = new SqlExplainInterceptor();
        return sqlExplainInterceptor;
    }

}
