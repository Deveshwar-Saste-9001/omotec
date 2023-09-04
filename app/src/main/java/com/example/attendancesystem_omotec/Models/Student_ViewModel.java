package com.example.attendancesystem_omotec.Models;

public class Student_ViewModel {
    private String RollNo;
    private String Name;
    private String  section;

    public Student_ViewModel(String rollNo, String name, String section) {
        RollNo = rollNo;
        Name = name;
        this.section = section;
    }

    public String getRollNo() {
        return RollNo;
    }

    public void setRollNo(String rollNo) {
        RollNo = rollNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
