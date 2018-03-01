package com.ey.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ey.annotations.PasswordMatches;
import com.ey.model.ApplicationUser;

/**
 * @author George.Rosario
 *
 */
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    
    
    BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {       
    }
    
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        ApplicationUser user = (ApplicationUser) obj;
        System.out.println("Password"+user.getPassword()+"\t"+"Confirm Password"+user.getConfirmPassword());
        
        //return user.getPassword().equals(user.getConfirmPassword()); 
        return bCryptPasswordEncoder.matches(user.getConfirmPassword(), user.getPassword());
        
    }     
}