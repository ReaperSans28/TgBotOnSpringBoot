package com.zoo.TgBotOnSpringBoot.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class User {
    
    private Long userId;
    private long userTgId;
    private String userContactDetails;
    private boolean isVoloteer;

    public User(Long userId, long userTgId, String userContactDetails, boolean isVoloteer){
        this.userId = userId;
        this.userTgId = userTgId;
        this.userContactDetails = userContactDetails;
        this.isVoloteer = isVoloteer;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public long getUserTgId() {
        return userTgId;
    }

    public void setUserTgId(long userTgId) {
        this.userTgId = userTgId;
    }

    public String getUserContactDetails() {
        return userContactDetails;
    }

    public void setUserContactDetails(String userContactDetails) {
        this.userContactDetails = userContactDetails;
    }

    public boolean isIsVoloteer() {
        return isVoloteer;
    }

    public void setIsVoloteer(boolean isVoloteer) {
        this.isVoloteer = isVoloteer;
    }
}
