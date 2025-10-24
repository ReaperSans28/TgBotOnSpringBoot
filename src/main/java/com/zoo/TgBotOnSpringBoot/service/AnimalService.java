package com.zoo.TgBotOnSpringBoot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zoo.TgBotOnSpringBoot.model.Animal;
import com.zoo.TgBotOnSpringBoot.repository.AnimalRepository;

@Service
public class AnimalService {
    
    @Autowired
    private AnimalRepository animalRepository;

    public Optional<Animal> findAnimal(long id) {
        return animalRepository.findById(id);
    }

    public Animal addAnimal(Animal animal) {
        return animalRepository.save(animal);
    }

    public Animal editAnimal(Animal animal) {
        return animalRepository.save(animal);
    }

    public void deleteAnimal(long id) {
        animalRepository.deleteById(id);
    }

    public List<Animal> getAnimalsByStatus(String status) {
        return animalRepository.getAnimalsByStatus(status);
    }
}
