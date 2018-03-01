
package com.ey.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.ey.annotations.PasswordMatches;
import com.ey.annotations.ValidEmail;

/**
 * @author George.Rosario
 *
 */
@Entity
@PasswordMatches
public class ApplicationUser implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long userId;
    @NotEmpty
    @NotNull
    @ValidEmail
    private String userName;
    @NotEmpty
    @NotNull
    private String password;
    @Transient
    private String confirmPassword;
    private boolean enabled;
    
    
    public ApplicationUser() {
        super();
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    

}