package com.feture.learnfilter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchMembersReq {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Mobile")
    private String mobile;

    @JsonProperty("CardNo")
    private String cardNo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    @Override
    public String toString() {
        return "SearchMembersReq{" +
                "name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", cardNo='" + cardNo + '\'' +
                '}';
    }
}
