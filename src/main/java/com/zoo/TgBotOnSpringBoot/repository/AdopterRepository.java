package com.zoo.TgBotOnSpringBoot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zoo.TgBotOnSpringBoot.model.Adopter;

public interface AdopterRepository extends JpaRepository<Adopter, Long> {
    
}
