package com.zoo.TgBotOnSpringBoot.model;

import jakarta.persistence.*;

@Entity
@Table(name = "animals")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long animalId;

    @Column(name = "animal_name")
    private String animalName;

    @Column(name = "animal_age")
    private Integer animalAge;

    @Column(name = "animal_description", columnDefinition = "TEXT")
    private String animalDescription;

    @Column(name = "animal_status")
    private String animalStatus;

    public Animal() {
    }

    public Animal(String animalName, Integer animalAge, String animalDescription, String animalStatus) {
        this.animalName = animalName;
        this.animalAge = animalAge;
        this.animalDescription = animalDescription;
        this.animalStatus = animalStatus;
    }

    public Long getAnimalId() {
        return animalId;
    }

    public void setAnimalId(Long animalId) {
        this.animalId = animalId;
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public Integer getAnimalAge() {
        return animalAge;
    }

    public void setAnimalAge(Integer animalAge) {
        this.animalAge = animalAge;
    }

    public String getAnimalDescription() {
        return animalDescription;
    }

    public void setAnimalDescription(String animalDescription) {
        this.animalDescription = animalDescription;
    }

    public String getAnimalStatus() {
        return animalStatus;
    }

    public void setAnimalStatus(String animalStatus) {
        this.animalStatus = animalStatus;
    }
}