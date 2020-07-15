package org.storage.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.core.enums.StoreFileStatus;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

/**
 * 多级存储层级
 */
@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "multipolar_store")
public class MultipolarStore implements Serializable {

    /**
     * 多级存储主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "snowflake-id")
    @GenericGenerator(name = "snowflake-id", strategy = "org.storage.configuration.SnowflakeIdGenerator")
    private Long multipolarStoreId;

    /**
     * 对应的存储文件主键
     */
    private Long fileId;

    /**
     * 实际存储在所属层级的状态
     */
    private StoreFileStatus status;

    /**
     * 存储文件级别
     */
    private Integer level;

    /**
     * 是否是当前激活层级
     */
    @Builder.Default
    private Boolean isActive = Boolean.FALSE;

    /**
     * 实际存储路径
     */
    private String storePath;

    /**
     * 存储文件大小
     */
    private Long storeSize;

    /**
     * 创建时间
     */
    private Instant createTime;

    /**
     * 更新时间
     */
    private Instant modifyTime;

}
