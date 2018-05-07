package com.freelancer.Entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Entity
@Table(name = "bid")
@EntityListeners(AuditingEntityListener.class)
public class Bid implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Column(name = "project_id")
    private Long projectId;

    @NotBlank
    private String bid_period;

    @NotBlank
    private String bid_amount;

    @NotBlank
    private String bid_status;

    public Bid(){

    }

    public Bid(Long id, @NotNull Long userId, @NotNull Long projectId, @NotBlank String bid_period, @NotBlank String bid_amount, @NotBlank String bid_status) {
        this.id = id;
        this.userId = userId;
        this.projectId = projectId;
        this.bid_period = bid_period;
        this.bid_amount = bid_amount;
        this.bid_status = bid_status;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
