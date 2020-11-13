package org.storage.service.impl;

import org.core.exception.EntityAlreadyExistsException;
import org.core.exception.EntityNotExistException;
import org.core.params.StoreFileParams;
import org.core.params.StoreSpaceParams;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.storage.entity.StoreSpace;
import org.storage.repository.StoreSpaceRepository;
import org.storage.service.StoreFileService;
import org.storage.service.StoreSpaceService;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * 存储空间服务
 *
 * @author panhong
 */
@Service
public class StoreSpaceServiceImpl implements StoreSpaceService {

    @Resource
    private StoreSpaceRepository storeSpaceRepository;

    @Resource
    private StoreFileService storeFileService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createStoreSpace(Long creatorId, StoreSpaceParams params) {
        if (storeSpaceRepository.exists(Example.of(StoreSpace.builder().name(params.getName())
                .isDeleted(Boolean.FALSE).build()))) {
            throw EntityAlreadyExistsException.build("此存储空间已存在");
        }
        StoreSpace storeSpace = StoreSpace.builder().name(params.getName()).description(params.getDescription())
                .isDeleted(false).createTime(Instant.now()).creatorId(creatorId).build();
        storeSpaceRepository.save(storeSpace);
        storeFileService.createStoreFileInfo(creatorId, StoreFileParams.builder()
                .storeSpaceId(storeSpace.getStoreSpacesId()).filePath("/".concat(params.getName()))
                .fileSize(0L).isDir(true).build());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStoreSpace(Long creatorId, StoreSpaceParams params) {
        Optional<StoreSpace> optional = storeSpaceRepository.findOne(Example.of(StoreSpace.builder()
                .creatorId(creatorId).name(params.getName()).isDeleted(Boolean.FALSE).build()));
        StoreSpace storeSpace = optional.orElseThrow(() -> EntityNotExistException.build("用户下此存储空间不存在"));
        storeSpace.setName(params.getName());
        storeSpace.setModifyTime(Instant.now());
    }

    @Override
    public List<StoreSpace> findStoreSpace(Long creatorId) {
        return storeSpaceRepository.findAll();
    }

}
