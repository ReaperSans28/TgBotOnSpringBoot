package com.zoo.TgBotOnSpringBoot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zoo.TgBotOnSpringBoot.model.Shelter;

public interface ShelterRepository extends JpaRepository<Shelter, Long> {
    
}
