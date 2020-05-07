package org.account.entity;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.time.Instant;

@Data
@MappedSuperclass
public class TimeEntity {

    /**
     * 创建时间
     */
    private Instant createTime;

    /**
     * 更新时间
     */
    private Instant modifyTime;

}
