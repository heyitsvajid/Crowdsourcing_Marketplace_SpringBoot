package com.freelancer.POJO;

public class BidDetail {
    private Long id;
    private Long userId;
    private Long projectId;
    private String bid_period;
    private String bid_amount;
    private String bid_status;
    private String userName;


    public BidDetail(Long id, Long userId, Long projectId, String bid_period, String bid_amount, String bid_status, String userName) {
        this.id = id;
        this.userId = userId;
        this.projectId = projectId;
        this.bid_period = bid_period;
        this.bid_amount = bid_amount;
        this.bid_status = bid_status;
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getBid_period() {
        return bid_period;
    }

    public void setBid_period(String bid_period) {
        this.bid_period = bid_period;
    }

    public String getBid_amount() {
        return bid_amount;
    }

    public void setBid_amount(String bid_amount) {
        this.bid_amount = bid_amount;
    }

    public String getBid_status() {
        return bid_status;
    }

    public void setBid_status(String bid_status) {
        this.bid_status = bid_status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
