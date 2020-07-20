package org.storage.service;

import org.core.params.StoreFileParams;

public interface StoreFileService {

    void createStoreFileInfo(Long creatorId, StoreFileParams params);

}
