package com.shortview.disposal.configuration;

import com.qiniu.util.Auth;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Author wanghui
 * @Date 2023/10/25 0025 15:49
 * @Version 1.0
 */
@Data
@Configuration
@Component
public class QiniuConfiguration {
    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Value("${qiniu.bucket}")
    private String bucket;

    @Bean
    public Auth qiniuAuth() {
        return Auth.create(accessKey, secretKey);
    }

}
