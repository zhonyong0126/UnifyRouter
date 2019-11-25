package com.feture.learnfilter.model;

public class HelloReq {

    private Integer a;

    private String b;

    public Integer getA() {
        return a;
    }

    public void setA(Integer a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return "HelloReq{" +
                "a=" + a +
                ", b='" + b + '\'' +
                '}';
    }
}
