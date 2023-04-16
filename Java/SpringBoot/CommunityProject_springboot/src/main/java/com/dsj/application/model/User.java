package com.dsj.application.model;

import lombok.Data;

@Data // 为当前实体类在编译期设置对应的get/set方法，toString方法，hashCode方法，equals方法等
public class User {
    private Integer id;
    private String username;
    private String password;
    private String repassword;
}
