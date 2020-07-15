package org.storage.repository;

import lombok.extern.slf4j.Slf4j;
import org.core.enums.AuthType;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.storage.StorageApplication;
import org.storage.entity.StoreBackend;

import javax.annotation.Resource;
import java.time.Instant;

@Slf4j
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StorageApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StoreBackendRepositoryTest {

    @Resource
    private StoreBackendRepository storeBackendRepository;

    @Test
    public void save() {
        StoreBackend storeBackend = new StoreBackend();
        storeBackend.setAuthType(AuthType.USER_PASSWORD);
        storeBackend.setCreateTime(Instant.now());
        storeBackend.setModifyTime(Instant.now());
        storeBackendRepository.save(storeBackend);
    }


}