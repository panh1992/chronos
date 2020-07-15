package org.account.message;

import lombok.extern.slf4j.Slf4j;
import org.account.AccountApplication;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessageUtilTest {

    @Resource
    private MessageUtil messageUtil;

    @Test
    public void sendMessage() {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (int i = 0; i < 2; i++) {
            executor.submit(() -> messageUtil.sendMessage());
        }
        executor.shutdown();
    }

}