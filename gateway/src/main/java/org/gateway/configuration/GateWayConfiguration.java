package org.gateway.configuration;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class GateWayConfiguration {

    /**
     * 注入 http信息转换器
     */
    @Bean
    public HttpMessageConverters httpMessageConverter() {
        return new HttpMessageConverters(new MappingJackson2HttpMessageConverter());
    }

}