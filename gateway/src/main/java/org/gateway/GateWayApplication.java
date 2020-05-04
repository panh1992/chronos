package org.gateway;

import org.gateway.configuration.Properties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringCloudApplication
@EnableConfigurationProperties(value = Properties.class)
@EnableFeignClients(basePackages = "org.gateway.feign")
public class GateWayApplication {

    public static void main(String[] args) {

        SpringApplication.run(GateWayApplication.class, args);

    }

}
