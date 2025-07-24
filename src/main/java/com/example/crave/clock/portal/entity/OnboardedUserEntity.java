package com.example.crave.clock.portal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "CC_ONBOARDED_USERS")
@Data
public class OnboardedUserEntity {
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

    @Column(name = "FULL_NAME")
    private String fullName;
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "EXPO_PUSH_TOKEN")
    private String expoPushToken;
}
