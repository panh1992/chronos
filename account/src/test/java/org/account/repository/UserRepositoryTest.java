package org.account.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.account.AccountApplication;
import org.account.entity.User;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.Optional;

@Slf4j
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserRepositoryTest {

    @Resource
    private ObjectMapper mapper;

    @Resource
    private UserRepository userRepository;

    @Test
    @Transactional
    public void save() {
        User user = User.builder().createTime(Instant.now()).username("test")
                .build();
        userRepository.save(user);
    }

    @Test
    @Transactional
    public void find() {
        Optional<User> optional = userRepository.findById(40107266078281728L);
        optional.ifPresent(user -> {
            try {
                System.out.println(mapper.writeValueAsString(user));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }

}
