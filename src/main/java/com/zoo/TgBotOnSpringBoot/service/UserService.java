package com.zoo.TgBotOnSpringBoot.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zoo.TgBotOnSpringBoot.model.User;
import com.zoo.TgBotOnSpringBoot.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findUser(long id) {
        return userRepository.findById(id);
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User editUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    public Boolean userExists(long userId) {
        return findUser(userId).isPresent();
    }

    public Optional<User> findByUserTgId(long chatId) {
        return userRepository.findByUserTgId(chatId);
    }
    
    public List<User> getAllVolonteers() {
        return userRepository.getAllVolonteers();
    }
}
