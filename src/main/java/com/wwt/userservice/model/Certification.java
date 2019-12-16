package com.wwt.userservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Document("Certification")
public class Certification {

    @Id
    @Field("_id")
    private String id;

    @Field("user_id")
    private String userId;

    @Field("org")
    private String org;

    @Field("name")
    private String name;

    @Field("tags")
    private List<Tag> tags;

    @Field("apply_time")
    private Date applyDate;

    @Field("deal_time")
    private Date dealDate;

    @Field("state")
    private String state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Date getDealDate() {
        return dealDate;
    }

    public void setDealDate(Date dealDate) {
        this.dealDate = dealDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Certification{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", org='" + org + '\'' +
                ", name='" + name + '\'' +
                ", tags=" + tags +
                ", applyDate=" + applyDate +
                ", dealDate=" + dealDate +
                ", state='" + state + '\'' +
                '}';
    }
}
