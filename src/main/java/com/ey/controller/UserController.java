
package com.ey.controller;

import java.util.Calendar;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.ey.dto.ApplicationUserDTO;
import com.ey.exceptions.EmailExistsException;
import com.ey.exceptions.RegistrationInvalidToken;
import com.ey.exceptions.RegistrationTokenExpiredException;
import com.ey.model.ApplicationUser;
import com.ey.model.VerificationToken;
import com.ey.service.UserService;

/**
 * @author George.Rosario
 * user controller class for Login and Registration of users
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    
    private static final Logger logger = LoggerFactory.getLogger(UserController.class); 
    
    @PostMapping("/signup")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ApplicationUser> register(@RequestBody @Valid ApplicationUserDTO userDTO,BindingResult result,Errors errors,HttpServletRequest request,WebRequest webReq )
    throws ConstraintViolationException, EmailExistsException, MessagingException{
        
        String path= "localhost:8080"+request.getContextPath();
       
        ApplicationUser user=null;
        if(!result.hasErrors()) {
           user = userService.createUser(userDTO);
        }
        
        
        if(user!=null) {
            this.userService.confirmRegistration(user,request.getLocale(),path);
        }
        
        return new ResponseEntity<ApplicationUser>(user, HttpStatus.OK);
        
    }
    
    @GetMapping("/regitrationConfirm")
    public ResponseEntity<ApplicationUser> confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token) throws RegistrationInvalidToken, RegistrationTokenExpiredException{
        Locale locale = request.getLocale();
        
        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
                throw new RegistrationInvalidToken("WrongToken: Cannot verify");
            
        }
        
        ApplicationUser user = verificationToken.getApplicationUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
                throw new RegistrationTokenExpiredException("Token: Expired");
        } 
        
        //user.setEnabled(true);
        int noOfRows=userService.updateUser(user);
        
        logger.info("No of Rows updaed after {}",noOfRows);
        
        if(noOfRows>=1) {
        	return new ResponseEntity<ApplicationUser>(user,HttpStatus.OK);
        }
        
        return null ;
        
    }

}