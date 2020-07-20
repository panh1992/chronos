package org.storage.service;

import lombok.extern.slf4j.Slf4j;
import org.core.params.StoreFileParams;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.storage.StorageApplication;

import javax.annotation.Resource;

@Slf4j
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StorageApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StoreFileServiceTest {

    @Resource
    private StoreFileService storeFileService;

    @Test
    public void saveFile() {
        String filePath = "/test/a/b/c.txt";
        storeFileService.createStoreFileInfo(1L, StoreFileParams.builder().storeSpaceId(123321L)
                .filePath(filePath).isDir(false).fileSize(2048L).build());
    }

}
