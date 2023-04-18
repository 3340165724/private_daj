package com.example.pojo;

import java.sql.Timestamp;

public class Bean {
    protected Integer id;
    protected String status;
    protected Timestamp timestamp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
