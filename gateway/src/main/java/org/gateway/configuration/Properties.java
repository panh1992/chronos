package org.gateway.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

/**
 * 自定义配置项
 *
 * @author panhong
 */
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
    public class Security {

        /**
         * Jwt token的配置项
         */
        @NestedConfigurationProperty
        private Jwt jwt = new Jwt();

        /**
         * 排除安全认证的uri
         */
        private String[] excludedUri;

        @Data
        public class Jwt {

            /**
             * JWT token 失效时间
             */
            private long expirationTimeMinutes;

            /**
             * JWT token 失效后可扩展的验证时间
             */
            private int maxFutureValidityInMinutes;

        }

    }

}
