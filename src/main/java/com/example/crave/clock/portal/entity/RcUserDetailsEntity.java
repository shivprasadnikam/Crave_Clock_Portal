package com.example.crave.clock.portal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "CC_USER_DETAILS")
@Data
public class RcUserDetailsEntity {
    @Id
    @Column(name = "USER_ID")
    private String userId;
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




}
