package org.account.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.account.AccountApplication;
import org.account.entity.Role;
import org.account.entity.User;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
//@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserRepositoryTest {

    @Resource
    private ObjectMapper mapper;

    @Resource
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(14);

    @Test
    @Transactional
    public void save() {
        User user = User.builder().userId(63351989043597312L).createTime(Instant.now()).username("test")
                .password(passwordEncoder.encode("123456")).build();

        user.setRoles(Stream.iterate(0, n -> n + 1).limit(100)
                .map(x -> Role.builder().name("角色测试".concat(x.toString())).createTime(Instant.now())
                        .description("角色备注".concat(x.toString()))
                        .resources(Lists.newArrayList())
                        .build())
                .collect(Collectors.toList()));

        userRepository.save(user);
    }

    @Test
    @Transactional
    public void find() {
        Optional<User> optional = userRepository.findById(63351989043597312L);
        optional.ifPresent(user -> {
            try {
                System.out.println(mapper.writeValueAsString(user));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }

}
