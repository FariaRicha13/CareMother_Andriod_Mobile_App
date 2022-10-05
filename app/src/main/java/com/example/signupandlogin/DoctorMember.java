package com.example.signupandlogin;
public class DoctorMember {
    private String Name;
    private String Phone;
    private String Hospital;
    private String BMDC;
    private  String uid;

    public  DoctorMember(){}

    public String getName() {
        return Name;
    }

    public String getPhone() {
        return Phone;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getHospital() {
        return Hospital;
    }

    public String getBMDC() {
        return BMDC;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setHospital(String hospital) {
        Hospital = hospital;
    }

    public void setBMDC(String bmdc) {
        BMDC = bmdc;
    }
}
