package org.storage.service.impl;

import org.core.params.StoreFileParams;
import org.core.params.StoreSpaceParams;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.storage.entity.StoreSpace;
import org.storage.repository.StoreSpaceRepository;
import org.storage.service.StoreFileService;
import org.storage.service.StoreSpaceService;

import javax.annotation.Resource;
import java.time.Instant;

@Service
public class StoreSpaceServiceImpl implements StoreSpaceService {

    @Resource
    private StoreSpaceRepository storeSpaceRepository;

    @Resource
    private StoreFileService storeFileService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createStoreSpace(Long creatorId, StoreSpaceParams params) {
        StoreSpace storeSpace = StoreSpace.builder().name(params.getName()).description(params.getDescription())
                .isDeleted(false).createTime(Instant.now()).modifyTime(Instant.now()).creatorId(creatorId).build();
        storeSpaceRepository.save(storeSpace);
        storeFileService.createStoreFileInfo(creatorId, StoreFileParams.builder()
                .filePath("/".concat(params.getName())).fileSize(0L).isDir(true).build());
    }

}
