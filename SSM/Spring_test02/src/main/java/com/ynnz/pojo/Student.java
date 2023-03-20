package com.ynnz.pojo;

public class Student {
    private Integer Sid;
    private String Sname;
    private Integer Sage;
    private String Ssex;
    private String Sclass;
    private String Sphone;

    public Student(Integer sid, String sname, Integer sage, String ssex, String sclass, String sphone) {
        Sid = sid;
        Sname = sname;
        Sage = sage;
        Ssex = ssex;
        Sclass = sclass;
        Sphone = sphone;
    }

    public Student() {

    }


    public Integer getSid() {
        return Sid;
    }

    public void setSid(Integer sid) {
        Sid = sid;
    }

    public String getSname() {
        return Sname;
    }

    public void setSname(String sname) {
        Sname = sname;
    }

    public Integer getSage() {
        return Sage;
    }

    public void setSage(Integer sage) {
        Sage = sage;
    }

    public String getSsex() {
        return Ssex;
    }

    public void setSsex(String ssex) {
        Ssex = ssex;
    }

    public String getSclass() {
        return Sclass;
    }

    public void setSclass(String sclass) {
        Sclass = sclass;
    }

    public String getSphone() {
        return Sphone;
    }

    public void setSphone(String sphone) {
        Sphone = sphone;
    }

    @Override
    public String toString() {
        return "Student{" +
                "Sid=" + Sid +
                ", Sname='" + Sname + '\'' +
                ", Sage=" + Sage +
                ", Ssex='" + Ssex + '\'' +
                ", Sclass='" + Sclass + '\'' +
                ", Sphone='" + Sphone + '\'' +
                '}';
    }
}
