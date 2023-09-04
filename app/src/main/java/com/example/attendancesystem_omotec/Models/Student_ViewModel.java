package com.example.attendancesystem_omotec.Models;

public class Student_ViewModel {
    private String RollNo;
    private String Name;
    private boolean absent;

    public Student_ViewModel(String rollNo, String name, boolean absent) {
        RollNo = rollNo;
        Name = name;
        this.absent = absent;
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

    public boolean isAbsent() {
        return absent;
    }

    public void setAbsent(boolean absent) {
        this.absent = absent;
    }
}
