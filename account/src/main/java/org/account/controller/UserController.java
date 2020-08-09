package org.account.controller;

import org.account.entity.User;
import org.account.message.MessageUtil;
import org.account.service.UserService;
import org.core.params.UserParams;
import org.core.resp.UserRes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private MessageUtil messageUtil;

    @GetMapping("/test")
    public String test() {
        messageUtil.sendMessage();
        return "Ok";
    }

    /**
     * 查询用户信息列表
     */
    @GetMapping
    public List<User> findAllUser() {
        return userService.findAllUser();
    }

    /**
     * 查询用户信息
     */
    @GetMapping("/{user_id}")
    public User userInfo(@PathVariable("user_id") Long userId) {
        return userService.findUserById(userId);
    }

    /**
     * 根据用户名查询用户信息
     */
    @GetMapping("/search")
    public UserRes getByUsername(@RequestParam("username") String username) {
        User user = userService.findByUsername(username);
        return UserRes.builder().userId(user.getUserId()).username(user.getUsername())
                .password(user.getPassword()).createTime(Instant.now()).build();
    }

    /**
     * 保存用户信息
     */
    @PostMapping
    public void save(@RequestBody UserParams userParams) {
        userService.save(userParams);
    }

}
