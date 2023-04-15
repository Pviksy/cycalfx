package com.pviksy.cycalfx.Entities;

import java.sql.Date;

public class Race {

    private int id;
    private String category_id;
    private String name;
    private java.sql.Date startDate;
    private java.sql.Date endDate;
    private String logo;
    private double distance;
    private String profileIcon;
    private String profile;

    public Race(int id, String category_id, String name, Date startDate, Date endDate, String logo, double distance, String profileIcon, String profile) {
        this.id = id;
        this.category_id = category_id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.logo = logo;
        this.distance = distance;
        this.profileIcon = profileIcon;
        this.profile = profile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getProfileIcon() {
        return profileIcon;
    }

    public void setProfileIcon(String profileIcon) {
        this.profileIcon = profileIcon;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "Race{" +
                "id=" + id +
                ", category_id='" + category_id + '\'' +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", logo='" + logo + '\'' +
                ", distance=" + distance +
                ", profileIcon='" + profileIcon + '\'' +
                ", profile='" + profile + '\'' +
                '}';
    }
}
