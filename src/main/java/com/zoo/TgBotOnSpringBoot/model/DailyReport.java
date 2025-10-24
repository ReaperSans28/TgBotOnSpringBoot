package com.zoo.TgBotOnSpringBoot.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class DailyReport {
    private long reportId;
    @ManyToOne
    private Adopter adopter;
    private String reportDetails;
    private Date reportDate;
    private boolean isApproved; //одобрен ли волонтёром

    public long getReportId() {
        return reportId;
    }

    public void setReportId(long reportId) {
        this.reportId = reportId;
    }

    public long getAdopterId() {
        return adopter.getAdopterId();
    }

    public void setAdopter(Adopter adopter) {
        this.adopter = adopter;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public boolean isIsApproved() {
        return isApproved;
    }

    public void setIsApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }

    public String getReportDetails() {
        return reportDetails;
    }

    public void setReportDetails(String reportDetails) {
        this.reportDetails = reportDetails;
    }
}
