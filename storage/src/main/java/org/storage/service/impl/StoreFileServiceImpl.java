package org.storage.service.impl;

import org.core.enums.FileStatus;
import org.core.params.StoreFileParams;
import org.core.util.PathUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.storage.entity.StoreFile;
import org.storage.repository.PathTreeRepository;
import org.storage.repository.StoreFileRepository;
import org.storage.service.StoreFileService;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Service
public class StoreFileServiceImpl implements StoreFileService {

    @Resource
    private StoreFileRepository storeFileRepository;

    @Resource
    private PathTreeRepository pathTreeRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createStoreFileInfo(Long creatorId, StoreFileParams params) {
        List<String> fileNames = PathUtil.getFileNames(params.getFilePath());
        String filePath = "";
        Long ancestorId = null;
        Instant currentTime = Instant.now();
        Iterator<String> iterator = fileNames.iterator();
        while (iterator.hasNext()) {
            String fileName = iterator.next();
            boolean isLast = !iterator.hasNext();
            filePath = filePath.concat("/").concat(fileName);
            Long fileId = storeFileRepository.findFileIdByStoreFilePath(params.getStoreSpaceId(), filePath, Boolean.FALSE);
            if (Objects.isNull(fileId)) {
                StoreFile storeFile = StoreFile.builder().creatorId(creatorId).name(fileName).createTime(currentTime)
                        .status(FileStatus.AVAILABLE).isDeleted(false).storeSpaceId(params.getStoreSpaceId())
                        .isDir(Boolean.TRUE).build();
                // 如果是最后一个文件名, 并且不是目录
                if (isLast && Boolean.FALSE.equals(params.getIsDir())) {
                    storeFile.setStatus(FileStatus.NEW);
                    storeFile.setSize(params.getFileSize());
                    storeFile.setIsDir(params.getIsDir());
                    storeFile.setFormat(PathUtil.getExtName(fileName));
                }
                storeFileRepository.save(storeFile);
                ancestorId = Objects.nonNull(ancestorId) ? ancestorId : storeFile.getStoreFileId();
                pathTreeRepository.savePathTree(ancestorId, storeFile.getStoreFileId(), currentTime);
                ancestorId = storeFile.getStoreFileId();
            } else {
                ancestorId = fileId;
            }
        }
    }

}
