package com.example.app.Models;

import java.sql.Date;

public class StudentDetails {
    private String name;
    private String regNum;
    private int level;
    private float gpa;
    private String department;
    private String dob;
    private String address;
    private String phone;
    private byte[] profilePic;

    public StudentDetails(String name, String regNum, int level, float gpa,
                          String department, String dob, String address, String phone, byte[] profilePic) {
        this.name = name;
        this.regNum = regNum;
        this.level = level;
        this.gpa = gpa;
        this.department = department;
        this.dob = dob;
        this.address = address;
        this.phone = phone;
        this.profilePic = profilePic;
    }

    // Getters
    public String getName() { return name; }
    public String getRegNum() { return regNum; }
    public int getLevel() { return level; }
    public float getGpa() { return gpa; }
    public String getDepartment() { return department; }
    public String getDob() { return dob; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public byte[] getProfilePic() { return profilePic; }
}
