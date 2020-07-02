package org.account.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 系统资源信息
 */
@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "resource")
public class Resource extends TimeEntity {

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
     * 资源对应的角色信息
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "role_id",
            foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Role role;

}
