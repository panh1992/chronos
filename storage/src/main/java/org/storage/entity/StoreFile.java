package org.storage.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.core.enums.FileStatus;
import org.core.enums.SourceType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.SecondaryTables;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

/**
 * 存储文件
 *
 * @author panhong
 */
@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "store_file")
@SecondaryTables(value = {
        @SecondaryTable(name = "store_file_info", pkJoinColumns = @PrimaryKeyJoinColumn(name = "store_file_id"),
                foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
})
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
     * 存储文件状态
     */
    private FileStatus status;

    /**
     * 用户角度文件大小 单位byte
     */
    @Column(table = "store_file_info")
    private Long size;

    /**
     * 文件格式, 以文件的拓展名为依据
     */
    @Column(table = "store_file_info")
    private String format;

    /**
     * 文件内容的校验码
     */
    @Column(table = "store_file_info")
    private String checksum;

    /**
     * 以文件内容或者文件元数据确定的文件的真实格式类型
     */
    @Column(table = "store_file_info")
    private String mimeType;

    /**
     * 文件存储来源主键
     */
    @Column(table = "store_file_info")
    private Long sourceId;

    /**
     * 文件存储来源类型
     */
    @Column(table = "store_file_info")
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
