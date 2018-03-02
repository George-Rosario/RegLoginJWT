package com.ey.service;

import java.util.Locale;

import javax.mail.MessagingException;
import javax.validation.ConstraintViolationException;

import com.ey.dto.ApplicationUserDTO;
import com.ey.exceptions.EmailExistsException;
import com.ey.model.ApplicationUser;
import com.ey.model.VerificationToken;

/**
 * @author George.Rosario
 *
 */
/**
 * @author George.Rosario
 *
 */
/**
 * @author George.Rosario
 *
 */
public interface UserService {

    /**
     * @param userDTO
     * @return
     * @throws ConstraintViolationException
     * @throws EmailExistsException
     */
    ApplicationUser createUser(ApplicationUserDTO userDTO) throws ConstraintViolationException, EmailExistsException;

    /**
     * @param user
     * @param locale
     * @param string
     * @throws MessagingException
     */
    void confirmRegistration(ApplicationUser user, Locale locale, String string) throws MessagingException;

    /**
     * @param token
     * @return
     */
    VerificationToken getVerificationToken(String token);

    /**
     * @param user
     */
    int updateUser(ApplicationUser user);

    
	/**
	 * @param userEmail
	 * @return
	 */
	ApplicationUser findUserByUserName(String userEmail);

	/**
	 * @param user
	 * @param locale
	 * @param path
	 * @throws MessagingException 
	 */
	void createPasswordResetTokenForUser(ApplicationUser user, Locale locale, String path) throws MessagingException;

}