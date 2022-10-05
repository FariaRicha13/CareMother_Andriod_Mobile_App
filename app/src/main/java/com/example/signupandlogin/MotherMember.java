package com.example.signupandlogin;
public class MotherMember {
    private String Phone;
    private String DOB;
    private String Weight;
    private String Height;
    private String Name;
    private String LMP;

    public String getLMP() {
        return LMP;
    }

    public void setLMP(String LMP) {
        this.LMP = LMP;
    }

    public String getWeek() {
        return Week;
    }

    public void setWeek(String week) {
        Week = week;
    }

    private String Week;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public  MotherMember(){}

    public String getPhone() {
        return Phone;
    }

    public String getDOB() {
        return DOB;
    }

    public String getWeight() {
        return Weight;
    }

    public String getHeight() {
        return Height;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public void setHeight(String height) {
        Height = height;
    }

}
