package org.account.service;

import org.account.entity.User;
import org.account.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Resource
    private UserRepository userRepository;

    public List<User> findAllUser() {
        return userRepository.findAll();
    }

}
