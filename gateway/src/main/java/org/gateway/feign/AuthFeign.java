package org.gateway.feign;

import org.core.exception.RemoteAccessException;
import org.core.resp.UserRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author panhong
 */
@FeignClient(name = "account", fallback = AuthFeign.AuthServiceHystrix.class)
public interface AuthFeign {

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名称
     * @return 用户信息
     */
    @GetMapping(value = "/auth/accounts/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    UserRes getByUsername(@PathVariable("username") String username);

    @Component
    class AuthServiceHystrix implements AuthFeign {

        @Override
        public UserRes getByUsername(String username) {
            throw RemoteAccessException.build("获取用户信息失败");
        }

    }

}
