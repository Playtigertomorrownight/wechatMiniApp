package com.smallrain.wechat.config;

import java.util.Properties;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;

@EnableTransactionManagement
@Configuration
@MapperScan("com.smallrain.wechat.models.*.mapper*")
public class MybatisPlusConfig {

  @Bean
  public PaginationInterceptor paginationInterceptor() {
    PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
    // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求 默认false
    // paginationInterceptor.setOverflow(false);
    // 设置最大单页限制数量，默认 500 条，-1 不受限制
    paginationInterceptor.setLimit(50);
    paginationInterceptor.setDialectType("mysql"); // 指定数据库类型
    return paginationInterceptor;
  }

  /**
   * Sql 输出
   * @return
   */
  @Bean
  public PerformanceInterceptor performanceInterceptor() {
    PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
    // 格式化sql语句
    Properties properties = new Properties();
    properties.setProperty("format", "false");
    performanceInterceptor.setProperties(properties);
    return performanceInterceptor;
  }

}