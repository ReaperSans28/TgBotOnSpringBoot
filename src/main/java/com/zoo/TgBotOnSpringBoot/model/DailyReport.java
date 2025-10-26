package com.zoo.TgBotOnSpringBoot.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "daily_reports")
public class DailyReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @ManyToOne
    @JoinColumn(name = "adopter_id")
    private Adopter adopter;

    @Column(name = "report_details", columnDefinition = "TEXT")
    private String reportDetails;

    @Column(name = "report_date")
    private Date reportDate;

    @Column(name = "is_approved")
    private boolean isApproved; // одобрен ли волонтёром

    public DailyReport() {
    }

    public DailyReport(Adopter adopter, String reportDetails, Date reportDate, boolean isApproved) {
        this.adopter = adopter;
        this.reportDetails = reportDetails;
        this.reportDate = reportDate;
        this.isApproved = isApproved;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public Adopter getAdopter() {
        return adopter;
    }

    public void setAdopter(Adopter adopter) {
        this.adopter = adopter;
    }

    public Long getAdopterId() {
        return adopter != null ? adopter.getAdopterId() : null;
    }

    public String getReportDetails() {
        return reportDetails;
    }

    public void setReportDetails(String reportDetails) {
        this.reportDetails = reportDetails;
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
}