package com.ztgeo.workbench;

import com.github.wxiaoqi.security.auth.client.EnableAceAuthClient;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAceAuthClient
@EnableEurekaClient
@EnableFeignClients({"com.github.wxiaoqi.security.auth.client.feign", "com.ztgeo.workbench.feign"})
@MapperScan("com.ztgeo.workbench.mapper")
@SpringBootApplication
// 开启事务
@EnableTransactionManagement
// 开启熔断监控
@EnableCircuitBreaker
@EnableSwagger2Doc
public class AceWorkbenchApplication {

    public static void main(String[] args) {
        SpringApplication.run(AceWorkbenchApplication.class, args);
    }
}
