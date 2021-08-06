package com.swagatsamal.DbClasses;

//Student POJO for easy access to DB;
public class StudentPOJO {
    int ID;
    String fullName;
    String programCode;
    String grade;
    String duration;
    Double fees;

    // Reminder: ID should only be get and not set as it is auto generated.
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Double getFees() {
        return fees;
    }

    public void setFees(Double fees) {
        this.fees = fees;
    }

    @Override
    public String toString() {
        return
                "StudentID: " + ID +
                " \n Full Name: " + fullName +
                " \n Program Code: " + programCode +
                " \n Grade: " + grade +
                " \n Duration: " + duration +
                " \n Fees: " + fees ;
    }
}
