package com.example.normand.Models;

/**
 * @author <Nguyen Tien Minh Quan - s3931082>
 */

import java.util.Date;
public class RentalAgreement {
    private String rentalId;
    private String propertyId;
    private String ownerId;
    private String hostId;
    private String tenantId;
    private Date startDate;
    private Date endDate;
    private double fee;
    private String status;

    public RentalAgreement(String rentalId, String propertyId, String ownerId, String hostId, String tenantId, Date startDate, Date endDate, double fee, String status) {
        this.rentalId = rentalId;
        this.propertyId = propertyId;
        this.ownerId = ownerId;
        this.hostId = hostId;
        this.tenantId = tenantId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.fee = fee;
        this.status = status;
    }

    public String getRentalId() {
        return rentalId;
    }

    public void setRentalId(String rentalId) {
        this.rentalId = rentalId;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return  "rentalId=" + rentalId +
                ", propertyId=" + propertyId +
                ", ownerId=" + ownerId +
                ", hostId=" + hostId +
                ", tenantId=" + tenantId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", fee=" + fee +
                ", status='" + status + '\'' +
                '}';
    }
}
