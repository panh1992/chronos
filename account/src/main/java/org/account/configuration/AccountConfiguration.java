package org.account.configuration;

import org.core.util.SnowflakeIdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * account 服务配置信息
 *
 * @author panhong
 */
@Configuration
public class AccountConfiguration {

    @Resource
    private Properties properties;

    @Bean
    public SnowflakeIdWorker idWorker() {
        return new SnowflakeIdWorker(properties.getSnowflakeId().getWorkerId(),
                properties.getSnowflakeId().getDataCenterId());
    }

}
