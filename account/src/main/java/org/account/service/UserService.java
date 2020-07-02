package org.account.service;

import org.account.entity.User;
import org.account.repository.UserRepository;
import org.core.exception.EntityNotExistException;
import org.core.params.UserParams;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Resource
    private UserRepository userRepository;

    /**
     * 查询用户信息列表
     */
    @Transactional(readOnly = true)
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    /**
     * 根据用户名获取用户信息
     */
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw EntityNotExistException.build("UserNotFound", "此用户不存在");
    }

    /**
     * 保存用户信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(UserParams userParams) {
        userRepository.save(User.builder().username(userParams.getUsername()).password(userParams.getPassword())
                .createTime(Instant.now()).modifyTime(Instant.now()).build());
    }

    /**
     * 根据用户id获取用户信息
     */
    @Transactional(readOnly = true)
    public User findUserById(long userId) {
        Optional<User> optional = userRepository.findById(userId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw EntityNotExistException.build("UserNotFound", "此用户不存在");
    }

}
