package com.ey.model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * @author George.Rosario
 *
 */
@Entity
public class VerificationToken {
    private static final int EXPIRATION = 60 * 24;
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    public VerificationToken() {
        super();
    }

    private String token;
    @OneToOne(targetEntity = ApplicationUser.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable=false, name= "userId" )
    private ApplicationUser applicationUser;
    private Date expiryDate;
    
    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public VerificationToken(String token, ApplicationUser applicationUser) {
        super();
        this.token = token;
        this.applicationUser = applicationUser;
        this.setExpiryDate(calculateExpiryDate(EXPIRATION));
    }
    

}