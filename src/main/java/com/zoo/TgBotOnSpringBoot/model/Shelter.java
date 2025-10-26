package com.zoo.TgBotOnSpringBoot.model;

import jakarta.persistence.*;

@Entity
@Table(name = "shelters")
public class Shelter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shelterId;

    @Column(name = "shelter_info", columnDefinition = "TEXT")
    private String shelterInfo;

    @Column(name = "address")
    private String address;

    @Column(name = "shelter_schedule")
    private String shelterSchedule;

    @Column(name = "shelter_security_contact")
    private String shelterSecurityContact;

    public Shelter() {
    }

    public Shelter(String shelterInfo, String address, String shelterSchedule, String shelterSecurityContact) {
        this.shelterInfo = shelterInfo;
        this.address = address;
        this.shelterSchedule = shelterSchedule;
        this.shelterSecurityContact = shelterSecurityContact;
    }

    public Long getShelterId() {
        return shelterId;
    }

    public void setShelterId(Long shelterId) {
        this.shelterId = shelterId;
    }

    public String getShelterInfo() {
        return shelterInfo;
    }

    public void setShelterInfo(String shelterInfo) {
        this.shelterInfo = shelterInfo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShelterSchedule() {
        return shelterSchedule;
    }

    public void setShelterSchedule(String shelterSchedule) {
        this.shelterSchedule = shelterSchedule;
    }

    public String getShelterSecurityContact() {
        return shelterSecurityContact;
    }

    public void setShelterSecurityContact(String shelterSecurityContact) {
        this.shelterSecurityContact = shelterSecurityContact;
    }
}