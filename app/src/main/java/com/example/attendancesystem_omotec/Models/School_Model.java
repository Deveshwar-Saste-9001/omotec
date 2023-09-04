package com.example.attendancesystem_omotec.Models;

public class School_Model {
    private  String school_ID;
    private String school_Name;
    private String school_Logo;
    private String school_location;

    public School_Model(String school_ID, String school_Name, String school_Logo, String school_location) {
        this.school_ID = school_ID;
        this.school_Name = school_Name;
        this.school_Logo = school_Logo;
        this.school_location = school_location;
    }

    public String getSchool_ID() {
        return school_ID;
    }

    public void setSchool_ID(String school_ID) {
        this.school_ID = school_ID;
    }

    public String getSchool_Name() {
        return school_Name;
    }

    public void setSchool_Name(String school_Name) {
        this.school_Name = school_Name;
    }

    public String getSchool_Logo() {
        return school_Logo;
    }

    public void setSchool_Logo(String school_Logo) {
        this.school_Logo = school_Logo;
    }

    public String getSchool_location() {
        return school_location;
    }

    public void setSchool_location(String school_location) {
        this.school_location = school_location;
    }
}
