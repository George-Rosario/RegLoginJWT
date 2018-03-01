package com.ey.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ey.model.VerificationToken;
import java.lang.String;

/**
 * @author George.Rosario
 *
 */
public interface TokenRepository extends JpaRepository<VerificationToken, Long> {
    
    VerificationToken findByToken(String token);

}