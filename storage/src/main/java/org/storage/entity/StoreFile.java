package org.storage.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.core.enums.FileStatus;
import org.core.enums.SourceType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

/**
 * 存储文件
 */
@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "store_file")
public class StoreFile implements Serializable {

    /**
     * 存储文件主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "snowflake-id")
    @GenericGenerator(name = "snowflake-id", strategy = "org.storage.configuration.SnowflakeIdGenerator")
    private Long storeFileId;

    /**
     * 存储空间主键
     */
    private Long storeSpaceId;

    /**
     * 创建者主键
     */
    private Long creatorId;

    /**
     * 存储文件名称
     */
    private String name;

    /**
     * 用户角度文件大小 单位byte
     */
    private Long size;

    /**
     * 存储文件状态
     */
    private FileStatus status;

    /**
     * 文件格式, 以文件的拓展名为依据
     */
    private String format;

    /**
     * 文件内容的MD5校验码
     */
    private String checksum;

    /**
     * 以文件内容或者文件元数据确定的文件的真实格式类型
     */
    private String mimeType;

    /**
     * 文件存储来源主键
     */
    private Long sourceId;

    /**
     * 文件存储来源类型
     */
    private SourceType sourceType;

    /**
     * 是否为目录
     */
    @Builder.Default
    private Boolean isDir = Boolean.FALSE;

    /**
     * 是否删除 (逻辑层面删除标识)
     */
    private Boolean isDeleted;

    /**
     * 创建时间
     */
    private Instant createTime;

    /**
     * 更新时间
     */
    private Instant modifyTime;

}
