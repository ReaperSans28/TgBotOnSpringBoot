package com.zoo.TgBotOnSpringBoot.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "user_tg_id")
    private Long userTgId;

    @Column(name = "user_contact_details")
    private String userContactDetails;

    @Column(name = "is_volunteer")
    private boolean isVolunteer;

    public User() {
    }

    public User(Long userId, Long userTgId, String userContactDetails, boolean isVolunteer) {
        this.userId = userId;
        this.userTgId = userTgId;
        this.userContactDetails = userContactDetails;
        this.isVolunteer = isVolunteer;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserTgId() {
        return userTgId;
    }

    public void setUserTgId(Long userTgId) {
        this.userTgId = userTgId;
    }

    public String getUserContactDetails() {
        return userContactDetails;
    }

    public void setUserContactDetails(String userContactDetails) {
        this.userContactDetails = userContactDetails;
    }

    public boolean isIsVolunteer() {
        return isVolunteer;
    }

    public void setIsVolunteer(boolean isVolunteer) {
        this.isVolunteer = isVolunteer;
    }
}