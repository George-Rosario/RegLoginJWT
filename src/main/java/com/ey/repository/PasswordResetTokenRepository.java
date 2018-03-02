package com.ey.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ey.model.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    
	PasswordResetTokenRepository findByToken(String token);

}