package com.zoo.TgBotOnSpringBoot.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zoo.TgBotOnSpringBoot.model.Adopter;
import com.zoo.TgBotOnSpringBoot.repository.AdopterRepository;

@Service
public class AdopterService {
    
    @Autowired
    private AdopterRepository adopterRepository;

    public Optional<Adopter> findAdopter(long id) {
        return adopterRepository.findById(id);
    }

    public Adopter addAdopter(Adopter adopter) {
        return adopterRepository.save(adopter);
    }

    public Adopter editAdopter(Adopter adopter) {
        return adopterRepository.save(adopter);
    }

    public void deleteAdopter(long id) {
        adopterRepository.deleteById(id);
    }
}
