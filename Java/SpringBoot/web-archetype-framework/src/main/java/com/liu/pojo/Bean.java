package com.liu.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;

public abstract class Bean {

    //	@Id
    //	@GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
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
