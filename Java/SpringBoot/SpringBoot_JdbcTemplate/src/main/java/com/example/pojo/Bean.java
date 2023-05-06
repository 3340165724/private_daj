package com.example.pojo;

import java.sql.Timestamp;

/**
 * - Bean 类封装抽象化公用属性
 */
public abstract class Bean {
    protected Integer id;
    protected String status;
    protected Timestamp timestamp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
