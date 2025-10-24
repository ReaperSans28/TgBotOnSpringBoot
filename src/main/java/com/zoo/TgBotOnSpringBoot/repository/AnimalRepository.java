package com.zoo.TgBotOnSpringBoot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zoo.TgBotOnSpringBoot.model.Animal;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    
    @Query(value = "select * from animal where animal_ststus = :status", nativeQuery=true)
    List<Animal> getAnimalsByStatus(@Param("status") String status);
}
