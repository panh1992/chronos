package org.storage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.core.enums.UploadTaskStatus;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

/**
 * 上传任务
 */
@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "upload_task")
public class UploadTask implements Serializable {

    /**
     * 上传任务主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "snowflake-id")
    @GenericGenerator(name = "snowflake-id", strategy = "org.storage.configuration.SnowflakeIdGenerator")
    private Long uploadTaskId;

    /**
     * 存储空间主键
     */
    private Long storeSpaceId;

    /**
     * 创建者主键
     */
    private Long creatorId;

    /**
     * 数据上传任务的目标存储后端主键
     */
    private Long targetStorageId;

    /**
     * 数据上传任务的目标存储后端上传参数1
     */
    private String targetStorageUploadArgs1;

    /**
     * 数据上传任务的目标存储后端上传参数2
     */
    private String targetStorageUploadArgs2;

    /**
     * 数据上传任务的目标存储后端上传参数3
     */
    private String targetStorageUploadArgs3;

    /**
     * 数据上传任务的状态
     */
    private UploadTaskStatus status;

    /**
     * 创建时间
     */
    private Instant createTime;

    /**
     * 更新时间
     */
    private Instant modifyTime;

    /**
     * 更新时间
     */
    private Instant finishTime;

    /**
     * 是否删除 (逻辑层面删除标识)
     */
    private Boolean isDeleted = Boolean.FALSE;

}
