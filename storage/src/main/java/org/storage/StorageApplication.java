package org.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.storage.configuration.Properties;

/**
 * 存储服务
 *
 * @author panhong
 */
@EnableTransactionManagement
@EnableConfigurationProperties(value = Properties.class)
@EnableJpaRepositories("org.storage.repository")
@SpringCloudApplication
public class StorageApplication {

    /**
     * 服务启动方法
     */
    public static void main(String[] args) {

        SpringApplication.run(StorageApplication.class, args);

    }

}
