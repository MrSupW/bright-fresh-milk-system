package com.mrsupw.config;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("com.mrsupw.mapper")
@EnableTransactionManagement
@Configuration
public class MybatisPlusConfig {
    //  注册乐观锁插件
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor(){
        return new OptimisticLockerInterceptor();
    }

    // 配置分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    // 配置逻辑删除
    @Bean
    public ISqlInjector iSqlInjector(){
        return new LogicSqlInjector();
    }

    // 配置SQL性能分析
    @Bean
    @Profile({"dev","test"})
        public PerformanceInterceptor performanceInterceptor(){
            PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        performanceInterceptor.setMaxTime(100);  // 设置sql执行的最长时间100ms
        performanceInterceptor.setFormat(false);
        return performanceInterceptor;
    }

}
