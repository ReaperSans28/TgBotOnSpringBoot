package com.zoo.TgBotOnSpringBoot.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "adopters")
public class Adopter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adopterId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "adopter_status")
    private String adopterStatus; // на тестовом периоде/ усыновил питомца/ провалил и тд

    public Adopter() {
    }

    public Adopter(User user, Date startDate, String adopterStatus) {
        this.user = user;
        this.startDate = startDate;
        this.adopterStatus = adopterStatus;
    }

    public Long getAdopterId() {
        return adopterId;
    }

    public void setAdopterId(Long adopterId) {
        this.adopterId = adopterId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Long getUserId() {
        return user != null ? user.getUserId() : null;
    }
}