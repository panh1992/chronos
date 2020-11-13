package org.storage.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.core.params.StoreSpaceParams;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.storage.StorageApplication;
import org.storage.entity.StoreSpace;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StorageApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class StoreSpaceServiceTest {

    private final static long CREATOR_ID = 63351989043597312L;

    @Resource
    private ObjectMapper mapper;

    @Resource
    private StoreSpaceService storeSpaceService;

    @Test
    public void createStoreSpace() {

        storeSpaceService.createStoreSpace(CREATOR_ID, StoreSpaceParams.builder().name("test")
                .description("test用户主存储空间").build());

    }

    @Test
    public void findStoreSpace() throws JsonProcessingException {
        List<StoreSpace> storeSpaces = storeSpaceService.findStoreSpace(CREATOR_ID);
        System.out.println(mapper.writeValueAsString(storeSpaces));
    }

}