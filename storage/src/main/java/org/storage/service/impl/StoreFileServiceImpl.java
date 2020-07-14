package org.storage.service.impl;

import org.core.util.PathUtil;
import org.springframework.stereotype.Service;
import org.storage.service.StoreFileService;

import java.util.List;

@Service
public class StoreFileServiceImpl implements StoreFileService {


    @Override
    public void saveFileInfo(String filePath, boolean isDir, Long fileSize) {
        List<String> fileNames = PathUtil.getFileNames(filePath);

    }
}
