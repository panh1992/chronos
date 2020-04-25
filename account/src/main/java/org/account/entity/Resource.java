package org.account.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

/**
 * 系统资源信息
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "resource", schema = "account")
public class Resource {

    /**
     * 资源主键
     */
    @Id
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
     * 创建时间
     */
    private Instant createTime;

    /**
     * 更新时间
     */
    private Instant modifyTime;

    /**
     * 权限描述
     */
    private String description;

}
