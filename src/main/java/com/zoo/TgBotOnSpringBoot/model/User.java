package com.zoo.TgBotOnSpringBoot.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class User {
    
    private long userId;
    private long userTgId;
    private boolean isRegistrated;
    private String userContactDetails;
    private boolean isVoloteer;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getUserTgId() {
        return userTgId;
    }

    public void setUserTgId(long userTgId) {
        this.userTgId = userTgId;
    }

    public boolean isIsRegistrated() {
        return isRegistrated;
    }

    public void setIsRegistrated(boolean isRegistrated) {
        this.isRegistrated = isRegistrated;
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
