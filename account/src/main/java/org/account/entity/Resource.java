package org.account.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

/**
 * 系统资源信息
 */
@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "resource")
public class Resource {

    /**
     * 资源主键
     */
    @Id
    @Column(name = "resource_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "snowflake-id")
    @GenericGenerator(name = "snowflake-id", strategy = "org.account.configuration.SnowflakeIdGenerator")
    private Long resourceId;

    /**
     * 所属模块
     */
    private String module;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 请求 URI
     */
    private String uri;

    /**
     * 请求 Method
     */
    private String method;

    /**
     * 资源标识
     */
    private String permission;

    /**
     * 权限描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Instant createTime;

    /**
     * 更新时间
     */
    private Instant modifyTime;

}
