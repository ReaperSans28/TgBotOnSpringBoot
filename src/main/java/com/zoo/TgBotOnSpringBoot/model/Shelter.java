package com.zoo.TgBotOnSpringBoot.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class Shelter {
    private long shelterId;
    private String shelterInfo;
    private String address;
    private String shelterSchedule;
    private String shelterSecurityContact;

    public long getShelterId() {
        return shelterId;
    }

    public void setShelterId(long shelterId) {
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
