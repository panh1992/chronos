package org.storage.configuration;

import org.core.util.SnowflakeIdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class StorageConfiguration {

    @Resource
    private Properties properties;

    @Bean
    public SnowflakeIdWorker idWorker() {
        return new SnowflakeIdWorker(properties.getSnowflakeId().getWorkerId(),
                properties.getSnowflakeId().getDataCenterId());
    }

}
