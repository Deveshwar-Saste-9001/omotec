package com.example.attendancesystem_omotec.Models;

public class Trainer_Model {
    private String emp_id;
    private String emp_profile_photo;
    private String emp_Name;
    private String emp_designation;
    private String emp_email;
    private long emp_contact;

    public Trainer_Model(String emp_id, String emp_profile_photo, String emp_Name, String emp_designation, String emp_email, long emp_contact) {
        this.emp_id = emp_id;
        this.emp_profile_photo = emp_profile_photo;
        this.emp_Name = emp_Name;
        this.emp_designation = emp_designation;
        this.emp_email = emp_email;
        this.emp_contact = emp_contact;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getEmp_profile_photo() {
        return emp_profile_photo;
    }

    public void setEmp_profile_photo(String emp_profile_photo) {
        this.emp_profile_photo = emp_profile_photo;
    }

    public String getEmp_Name() {
        return emp_Name;
    }

    public void setEmp_Name(String emp_Name) {
        this.emp_Name = emp_Name;
    }

    public String getEmp_designation() {
        return emp_designation;
    }

    public void setEmp_designation(String emp_designation) {
        this.emp_designation = emp_designation;
    }

    public String getEmp_email() {
        return emp_email;
    }

    public void setEmp_email(String emp_email) {
        this.emp_email = emp_email;
    }

    public long getEmp_contact() {
        return emp_contact;
    }

    public void setEmp_contact(long emp_contact) {
        this.emp_contact = emp_contact;
    }
}
