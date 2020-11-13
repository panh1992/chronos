package org.storage.service;

import org.core.params.StoreFileParams;

public interface StoreFileService {

    /**
     * 创建文件信息
     *
     * @param creatorId 创建者主键
     * @param params    存储参数
     */
    void createStoreFileInfo(Long creatorId, StoreFileParams params);

}
