package com.example.crave.clock.portal.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "RC_USER_DETAILS")
public class RcUserDetailsEntity {
    @Id
    @Column(name = "USERNAME")
    private String username;
    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "STATUS")
    private String status;
    @Column(name = "SESSION_TOKEN")
    private String sessionToken;
    @Column(name = "CREATED_DATE")
    private Timestamp createdDate;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

}
