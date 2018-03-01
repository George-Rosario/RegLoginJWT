package com.ey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ey.model.ApplicationUser;
import java.lang.String;

/**
 * @author George.Rosario
 *
 */
@Repository
public interface UserRepository extends JpaRepository<ApplicationUser, Long> {
    
    ApplicationUser findByUserName(String username);
    
    /**
     * @param status
     * @param userName
     */
    @Modifying
    @Query("update ApplicationUser u set u.enabled=?1 where u.userName=?2")
    int setEnabledStatus(boolean status, String userName );
}