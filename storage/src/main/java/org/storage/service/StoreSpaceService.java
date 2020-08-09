package org.storage.service;

import org.core.params.StoreSpaceParams;
import org.storage.entity.StoreSpace;

import java.util.List;

/**
 * 存储空间服务
 *
 * @author panhong
 */
public interface StoreSpaceService {

    /**
     * 创建存储空间
     *
     * @param creatorId 操作人主键
     * @param params    存储空间参数
     */
    void createStoreSpace(Long creatorId, StoreSpaceParams params);

    /**
     * 修改存储空间
     *
     * @param creatorId 操作人主键
     * @param params    存储空间参数
     */
    void updateStoreSpace(Long creatorId, StoreSpaceParams params);

    List<StoreSpace> findStoreSpace(Long creatorId);

}
