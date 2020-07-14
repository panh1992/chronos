package org.storage.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.storage.StorageApplication;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StorageApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StoreFileServiceTest {

    @Resource
    private StoreFileService storeFileService;

    @Test
    public void saveFile() {
        String filePath = "/Users/panhong/Projects";
        storeFileService.saveFileInfo(filePath, true, null);
    }

}