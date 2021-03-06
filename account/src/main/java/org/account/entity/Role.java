package org.account.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * 角色信息
 *
 * @author panhong
 */
@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
public class Role implements Serializable {

    /**
     * 角色主键
     */
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "snowflake-id")
    @GenericGenerator(name = "snowflake-id", strategy = "org.account.configuration.SnowflakeIdGenerator")
    private Long roleId;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色描述
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

    /**
     * 角色下资源信息
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "role_resource_ref",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "resource_id", referencedColumnName = "resource_id"),
            foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT),
            inverseForeignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private List<Resource> resources;

}
