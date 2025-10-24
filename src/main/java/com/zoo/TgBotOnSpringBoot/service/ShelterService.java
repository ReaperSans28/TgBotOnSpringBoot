package com.zoo.TgBotOnSpringBoot.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zoo.TgBotOnSpringBoot.model.Shelter;
import com.zoo.TgBotOnSpringBoot.repository.ShelterRepository;

@Service
public class ShelterService {

    @Autowired
    private ShelterRepository shelterRepository;

    public Optional<Shelter> findshelter(long id) {
        return shelterRepository.findById(id);
    }

    public Shelter addshelter(Shelter shelter) {
        return shelterRepository.save(shelter);
    }

    public Shelter editshelter(Shelter shelter) {
        return shelterRepository.save(shelter);
    }

    public void deleteshelter(long id) {
        shelterRepository.deleteById(id);
    }
}
