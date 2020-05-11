package org.gateway;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = GateWayApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedisTest {

    @Resource
    private ReactiveRedisTemplate<String, Matcher> reactiveRedisTemplate;

    @Resource
    private ReactiveStringRedisTemplate reactiveStringRedisTemplate;

    private static final String testKey = "redis_test_key";

    @Test
    public void test01() {
        for (int i = 0; i < 10000; i++) {
            long num = reactiveRedisTemplate.opsForSet().add(testKey + i, new Matcher("GET", "/auth/accounts"),
                    new Matcher("GET", "/auth/accounts/123")).blockOptional().orElse(0L);
            Assert.assertEquals(2L, num);
        }
    }

    @Test
    public void test02() {
        boolean result = reactiveRedisTemplate.opsForSet().delete("panhong").blockOptional().orElse(false);
        Assert.assertTrue(result);
    }

    @Test
    public void test03() {
        Assert.assertTrue(reactiveStringRedisTemplate.opsForHash()
                .put(testKey, "aslsms", "GET/auth/account").blockOptional().orElse(false));
        Assert.assertTrue(reactiveStringRedisTemplate.opsForHash()
                .put(testKey, "Tghjk", "GET/auth/accounts/123").blockOptional().orElse(false));
    }

    @Test
    public void test04() {
        Assert.assertEquals(reactiveStringRedisTemplate.opsForHash().remove(testKey, "aslsms")
                .blockOptional().orElse(0L).longValue(), Long.parseLong("1"));
    }

    @Test
    public void test05() {
        List<Map.Entry<Object, Object>> entries = reactiveStringRedisTemplate.opsForHash().scan(testKey, ScanOptions.scanOptions()
                .match("Tghjk").build()).collectList().blockOptional().orElse(Lists.newArrayList());
        System.out.println(entries);
        Assert.assertEquals(entries.size(), Integer.parseInt("1"));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class Matcher {

        private String method;

        private String uri;

    }

}
