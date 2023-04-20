package com.dsj.pojo;

public class Student {
    public String SId;
    public String Sname;
    public String Sage;
    public String Ssex;

    public String getSId() {
        return SId;
    }

    public void setSId(String SId) {
        this.SId = SId;
    }

    public String getSname() {
        return Sname;
    }

    public void setSname(String sname) {
        Sname = sname;
    }

    public String getSage() {
        return Sage;
    }

    public void setSage(String sage) {
        Sage = sage;
    }

    public String getSsex() {
        return Ssex;
    }

    public void setSsex(String ssex) {
        Ssex = ssex;
    }

    @Override
    public String toString() {
        return "Student{" +
                "SId='" + SId + '\'' +
                ", Sname='" + Sname + '\'' +
                ", Sage='" + Sage + '\'' +
                ", Ssex='" + Ssex + '\'' +
                '}';
    }
}
