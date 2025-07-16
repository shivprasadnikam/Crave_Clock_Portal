package com.example.crave.clock.portal.repository;

import com.example.crave.clock.portal.entity.RcUserDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RcUserDetailsRepository extends JpaRepository<RcUserDetailsEntity,Long> {
    @Query(value = "SELECT CC_USER_ID_SEQ.NEXTVAL FROM DUAL", nativeQuery = true)
    String getUserIdSeq();

    Optional<RcUserDetailsEntity> findByUsername(String userName);

    @Query(value = "SELECT USER_ID FROM CC_USER_DETAILS WHERE USERNAME =:userName", nativeQuery = true)
    String getUserIdByUserName(@Param("userName") String userName);



}
