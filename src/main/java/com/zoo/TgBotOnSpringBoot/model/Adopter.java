package com.zoo.TgBotOnSpringBoot.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Adopter {
    private long adopterId;

    @OneToMany
    private User user;
    private Date startDate;
    private String adopterStatus; //на тестовом периоде/ усыновил питомца/ провалил и тд

    public long getAdopterId() {
        return adopterId;
    }

    public void setAdopterId(long adopterId) {
        this.adopterId = adopterId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getAdopterStatus() {
        return adopterStatus;
    }

    public void setAdopterStatus(String adopterStatus) {
        this.adopterStatus = adopterStatus;
    }

    public long getUserId() {
        return user.getUserId();
    }

    public void setUser(User user) {
        this.user = user;
    }
}
