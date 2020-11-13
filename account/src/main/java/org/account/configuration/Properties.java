package org.account.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Data
@ConfigurationProperties(prefix = "chronos")
public class Properties {

    @NestedConfigurationProperty
    private SnowflakeId snowflakeId = new SnowflakeId();

    @Data
    protected static class SnowflakeId {

        /**
         * 工作机器ID (0~31)
         */
        private Long workerId;

        /**
         * 数据中心ID (0~31)
         */
        private Long dataCenterId;

    }

}
