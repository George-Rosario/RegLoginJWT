package com.ey.service;

import java.util.Locale;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ey.dto.ApplicationUserDTO;
import com.ey.exceptions.EmailExistsException;
import com.ey.model.ApplicationUser;
import com.ey.model.PasswordResetToken;
import com.ey.model.VerificationToken;
import com.ey.repository.PasswordResetTokenRepository;
import com.ey.repository.TokenRepository;
import com.ey.repository.UserRepository;

/**
 * @author George.Rosario
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userrepo;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	TokenRepository tokenRepository;
	
	@Autowired
	PasswordResetTokenRepository passwordResetTokenRepository;

	@Autowired
	private JavaMailSender sender;

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	/**
	 * @param userDTO
	 * @return
	 */
	private ApplicationUser convertUserDtoToModel(ApplicationUserDTO userDTO) {
		logger.debug("Converting DTO to Model {}", userDTO);
		ApplicationUser user = new ApplicationUser();
		BeanUtils.copyProperties(userDTO, user);
		user.setEnabled(false); // By Default all new users should be disabled
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ey.service.UserService#createUser(com.ey.dto.ApplicationUserDTO)
	 */
	@Override
	public ApplicationUser createUser(ApplicationUserDTO userDTO)
			throws ConstraintViolationException, EmailExistsException {
		ApplicationUser user = convertUserDtoToModel(userDTO);
		user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));

		if (emailExist(userDTO.getUserName())) {
			throw new EmailExistsException("There is an account with that email adress: " + userDTO.getUserName());
		}

		return userrepo.save(user);
	}

	/**
	 * @param username
	 * @return
	 */
	private boolean emailExist(String username) {
		ApplicationUser user = userrepo.findByUserName(username);
		if (user != null) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ey.service.UserService#confirmRegistration(com.ey.model.ApplicationUser,
	 * java.util.Locale, java.lang.String)
	 */
	@Override
	public void confirmRegistration(ApplicationUser user, Locale locale, String url) throws MessagingException {
		String token = UUID.randomUUID().toString();
		createVerificationToken(user, token);
		String recipientAddress = user.getUserName();
		String subject = "Registration Confirmation";
		String confirmationUrl = "http://" + url + "/user/regitrationConfirm?token=" + token;

		MimeMessage mimeMessages = sender.createMimeMessage();
		String msg = "<body style='border:2px solid black'>" + "Your  registration link  "
				+ "Please use this link to complete your new user registration."
				+ "link is confidential, do not share this  with anyone."
				// +"<a
				// href=\"http://localhost:8080/registration/user/regitrationConfirm?token=8baed28b-c2dc-48e9-9543-9cd41e3f9d1b\">CLICK
				// HERE</a>"
				+ "<a href=\"" + confirmationUrl + "\">CLICK HERE</a>" + "</body>";

		MimeMessageHelper helper = new MimeMessageHelper(mimeMessages, false, "utf-8");
		mimeMessages.setContent(msg, "text/html");
		helper.setTo(recipientAddress);
		// helper.setText(confirmationUrl,);
		helper.setSubject(subject);
		sender.send(mimeMessages);

	}

	/**
	 * @param user
	 * @param token
	 */
	private void createVerificationToken(ApplicationUser user, String token) {
		VerificationToken verificationToken = new VerificationToken(token, user);
		tokenRepository.save(verificationToken);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ey.service.UserService#getVerificationToken(java.lang.String)
	 */
	@Override
	public VerificationToken getVerificationToken(String token) {
		return tokenRepository.findByToken(token);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ey.service.UserService#updateUser(com.ey.model.ApplicationUser)
	 */
	@Override
	@Transactional
	public int updateUser(ApplicationUser user) {
		return userrepo.setEnabledStatus(true, user.getUserName());

	}

	/* (non-Javadoc)
	 * @see com.ey.service.UserService#findUserByUserName(java.lang.String)
	 */
	@Override
	public ApplicationUser findUserByUserName(String userEmail) {
		return userrepo.findByUserName(userEmail);
	}

	/* (non-Javadoc)
	 * @see com.ey.service.UserService#createPasswordResetTokenForUser(com.ey.model.ApplicationUser, java.util.Locale, java.lang.String)
	 */
	@Override
	public void createPasswordResetTokenForUser(ApplicationUser user, Locale locale, String path) throws MessagingException {
		String token = UUID.randomUUID().toString();
		createPasswordResetToken(user, token);
		String recipientAddress = user.getUserName();
		String subject = "Reset Password Email";
		String confirmationUrl = "http://" + path + "/user/resetPasswordConfirm?token=" + token;

		MimeMessage mimeMessages = sender.createMimeMessage();
		String msg = "<body style='border:2px solid black'>" + "Your  Reset link  "
				+ "Please use this link to complete your new reset process."
				+ "link is confidential, do not share this  with anyone."
				+ "<a href=\"" + confirmationUrl + "\">CLICK HERE</a>" + "</body>";

		MimeMessageHelper helper = new MimeMessageHelper(mimeMessages, false, "utf-8");
		mimeMessages.setContent(msg, "text/html");
		helper.setTo(recipientAddress);
		helper.setSubject(subject);
		sender.send(mimeMessages);
	}

	
	/**
	 * @param user
	 * @param token
	 */
	private void createPasswordResetToken(ApplicationUser user, String token) {
		PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);
		passwordResetTokenRepository.save(passwordResetToken);
		
	}

	@Override
	public PasswordResetToken getPassWordRestToken(String token) {
		return passwordResetTokenRepository.findByToken(token);
	}

}