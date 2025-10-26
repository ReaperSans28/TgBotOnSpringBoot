package com.zoo.TgBotOnSpringBoot.repository;

import com.zoo.TgBotOnSpringBoot.model.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShelterRepository extends JpaRepository<Shelter, Long> {

}