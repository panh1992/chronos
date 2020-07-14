package org.storage.service;

public interface StoreFileService {

    void saveFileInfo(String filePath, boolean isDir, Long fileSize);

}
