package com.bytehonor.sdk.beautify.netty.sample;

import java.io.Serializable;

public class SampleUser implements Serializable {

    private static final long serialVersionUID = 2072360221332110919L;
    
    private Integer id;

    public static SampleUser of(int id) {
        SampleUser model = new SampleUser();
        model.setId(id);
        return model;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
