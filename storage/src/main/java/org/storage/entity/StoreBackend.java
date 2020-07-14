package org.storage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.core.enums.AuthType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

/**
 * 存储后端
 */
@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "store_backend")
public class StoreBackend implements Serializable {

    /**
     * 存储后端主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "snowflake-id")
    @GenericGenerator(name = "snowflake-id", strategy = "org.storage.configuration.SnowflakeIdGenerator")
    private Long storeBackendId;

    /**
     * 存储后端名称
     */
    private String name;

    /**
     * 存储协议
     */
    private String protocol;

    /**
     * 存储级别
     */
    private Integer level;

    /**
     * 是否有效
     */
    private Boolean isActive;

    /**
     * 存储后端的根空间
     */
    private String container;

    /**
     * 存储后端的服务地址
     */
    private String host;

    /**
     * 存储后端的服务端口
     */
    private String port;

    /**
     * 访问认证方式
     */
    private AuthType authType;

    /**
     * 访问认证方式参数1
     */
    private String authParam1;

    /**
     * 访问认证方式参数2
     */
    private String authParam2;

    /**
     * 访问认证方式参数3
     */
    private String authParam3;

    /**
     * 访问认证方式参数4
     */
    private String authParam4;

    /**
     * 存储后端的服务地址
     */
    private String innerHost;

    /**
     * 存储后端的服务端口
     */
    private String innerPort;

    /**
     * 内部访问认证方式
     */
    private AuthType innerAuthType;

    /**
     * 内部访问认证方式参数1
     */
    private String innerAuthParam1;

    /**
     * 内部访问认证方式参数2
     */
    private String innerAuthParam2;

    /**
     * 内部访问认证方式参数3
     */
    private String innerAuthParam3;

    /**
     * 内部访问认证方式参数4
     */
    private String innerAuthParam4;

    /**
     * 创建时间
     */
    private Instant createTime;

    /**
     * 更新时间
     */
    private Instant modifyTime;

}
