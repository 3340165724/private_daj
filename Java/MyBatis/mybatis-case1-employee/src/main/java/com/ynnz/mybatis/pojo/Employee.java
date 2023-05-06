package com.ynnz.mybatis.pojo;

/**
 * @author liKaihusing
 * @since  1.0
 * @version 1.0
 */
public class Employee {
    private Integer id;
    private String name;
    private String gender;
    private String position;
    private String nationlity;


    //无参构造

    public Employee() {
    }

    //有参构造
    public Employee(Integer id, String name, String gender, String position, String nationlity) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.position = position;
        this.nationlity = nationlity;
    }

    //set and get

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getNationlity() {
        return nationlity;
    }

    public void setNationlity(String nationlity) {
        this.nationlity = nationlity;
    }

    @Override
    public String toString() {
        return "employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", position='" + position + '\'' +
                ", nationlity='" + nationlity + '\'' +
                '}';
    }
}
