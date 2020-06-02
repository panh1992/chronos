package org.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
public class StorageApplication {

    /**
     * 服务启动方法
     */
    public static void main(String[] args) {

        SpringApplication.run(StorageApplication.class, args);

    }

}
