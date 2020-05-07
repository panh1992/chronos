package org.account.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "role", schema = "account")
public class Role extends TimeEntity {

    /**
     * 角色主键
     */
    @Id
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

}
