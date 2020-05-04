package org.gateway.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.core.util.CommonUtil;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import javax.annotation.Resource;

@Configuration
public class GateWayConfiguration {

    @Resource
    private ObjectMapper mapper;

    @Bean
    public ObjectMapper mapper() {
        return CommonUtil.getObjectMapper();
    }

    /**
     * 注入 http信息转换器
     */
    @Bean
    public HttpMessageConverters httpMessageConverter() {
        return new HttpMessageConverters(new MappingJackson2HttpMessageConverter(mapper));
    }

}