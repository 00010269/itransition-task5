package com.itransition.task5.service;

import com.itransition.task5.entity.User;
import com.itransition.task5.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User findByName(User userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            return userRepository.findByUsername(userDto.getUsername()).get();
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        return userRepository.save(user);
    }

}


