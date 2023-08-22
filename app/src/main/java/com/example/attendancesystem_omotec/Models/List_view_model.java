package com.example.attendancesystem_omotec.Models;

public class List_view_model {
    private int roll_No;
    private String student_Name;
    private String student_Section;
    private boolean student_Absent;

    public List_view_model(int roll_No, String student_Name, String student_Section, boolean student_Absent) {
        this.roll_No = roll_No;
        this.student_Name = student_Name;
        this.student_Section = student_Section;
        this.student_Absent = student_Absent;
    }

    public int getRoll_No() {
        return roll_No;
    }

    public void setRoll_No(int roll_No) {
        this.roll_No = roll_No;
    }

    public String getStudent_Name() {
        return student_Name;
    }

    public void setStudent_Name(String student_Name) {
        this.student_Name = student_Name;
    }

    public String getStudent_Section() {
        return student_Section;
    }

    public void setStudent_Section(String student_Section) {
        this.student_Section = student_Section;
    }

    public boolean isStudent_Absent() {
        return student_Absent;
    }

    public void setStudent_Absent(boolean student_Absent) {
        this.student_Absent = student_Absent;
    }
}
