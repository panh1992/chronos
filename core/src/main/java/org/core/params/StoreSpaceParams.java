package org.core.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 创建存储空间参数
 * @author panhong
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreSpaceParams {

    /**
     * 存储空间名称
     */
    private String name;

    /**
     * 存储空间描述信息
     */
    private String description;

}
