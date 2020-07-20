package org.storage.service;

import org.core.params.StoreSpaceParams;

public interface StoreSpaceService {

    void createStoreSpace(Long creatorId, StoreSpaceParams params);

}
