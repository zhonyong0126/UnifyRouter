package com.feture.learnfilter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TestCacheModel {

    @JsonProperty("Key")
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "TestCacheModel{" +
                "key='" + key + '\'' +
                '}';
    }
}
