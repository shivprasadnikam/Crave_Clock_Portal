package com.example.crave.clock.portal.repository;

import com.example.crave.clock.portal.entity.OnboardedUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RcUserDetailsRepository extends JpaRepository<OnboardedUserEntity, Long> {
    @Query(value = "SELECT nextval('cc_user_id_seq')", nativeQuery = true)
    String getUserIdSeq();

    Optional<OnboardedUserEntity> findByUsername(String userName);

    @Query(value = "SELECT USER_ID FROM CC_ONBOARDED_USERS WHERE USERNAME =:userName", nativeQuery = true)
    String getUserIdByUserName(@Param("userName") String userName);

}
