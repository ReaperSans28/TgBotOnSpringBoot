package com.zoo.TgBotOnSpringBoot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zoo.TgBotOnSpringBoot.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUserTgId(long userTgId);

    @Query(value = "select * from users where is_volunteer = True", nativeQuery=true)
    List<User> getAllVolonteers();
}
