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
 * 文件的树形结构存储信息
 */
@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "path_tree")
public class PathTree implements Serializable {

    /**
     * 文件树形结构主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "snowflake-id")
    @GenericGenerator(name = "snowflake-id", strategy = "org.storage.configuration.SnowflakeIdGenerator")
    private Long pathTreeId;

    /**
     * 路径树的祖先文件主键
     */
    private Long ancestorId;

    /**
     * 路径树的子孙文件主键
     */
    private Long descendantId;

    /**
     * 相对层级深度,自我引用为0
     */
    private Integer depth;

    /**
     * 是否删除 (逻辑层面删除标识)
     */
    @Builder.Default
    private Boolean isDeleted = Boolean.FALSE;

    /**
     * 创建时间
     */
    private Instant createTime;

    /**
     * 更新时间
     */
    private Instant modifyTime;

}
