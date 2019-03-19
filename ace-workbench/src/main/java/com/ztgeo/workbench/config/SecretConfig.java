package com.ztgeo.workbench.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 秘钥的配置类
 * Create by Wei on 2018/9/6
 *
 */
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "secret")
public class SecretConfig {
    private  String WFpriKey;//平台私钥
    private  String WFpubKey;//平台公钥
}
