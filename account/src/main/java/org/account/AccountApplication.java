package org.account;

import org.account.configuration.Properties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 账户服务
 *
 * @author panhong
 */
@SpringCloudApplication
@EnableTransactionManagement
@EnableConfigurationProperties(value = Properties.class)
@EnableJpaRepositories("org.account.repository")
public class AccountApplication {

    /**
     * 服务启动方法
     */
    public static void main(String[] args) {

        SpringApplication.run(AccountApplication.class, args);

    }

}
