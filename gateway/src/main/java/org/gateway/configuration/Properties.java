package org.gateway.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

@Data
@Component("properties")
@ConfigurationProperties(prefix = "chronos")
public class Properties {

    /**
     * 安全相关配置信息
     */
    @NestedConfigurationProperty
    private Security security = new Security();

    @Data
    protected class Security {

        /**
         * 排除安全认证的uri
         */
        private String[] excludedUri;

    }

}
