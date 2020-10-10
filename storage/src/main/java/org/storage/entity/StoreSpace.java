package org.storage.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

/**
 * 存储空间
 *
 * @author panhong
 */
@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "store_space")
public class StoreSpace implements Serializable {

    /**
     * 存储空间主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "snowflake-id")
    @GenericGenerator(name = "snowflake-id", strategy = "org.storage.configuration.SnowflakeIdGenerator")
    private Long storeSpacesId;

    /**
     * 创建者主键
     */
    private Long creatorId;

    /**
     * 存储空间名称
     */
    private String name;

    /**
     * 是否删除 (逻辑层面删除标识)
     */
    @Builder.Default
    private Boolean isDeleted = Boolean.FALSE;

    /**
     * 存储空间描述信息
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
