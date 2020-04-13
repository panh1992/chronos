package org.account.service;

import org.account.entity.User;
import org.account.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class AuthService {

    @Resource
    private UserRepository userRepository;

    public String login(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.get().getUsername();
    }

}
