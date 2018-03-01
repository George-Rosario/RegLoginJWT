package com.ey.exceptions;


/**
 * @author George.Rosario
 *
 */
public class EmailExistsException extends Exception {
   
    private static final long serialVersionUID = 1L;
    
    private String message;
    
    

    public String getMessage() {
        return message;
    }

    public EmailExistsException(String message) {
        super();
        this.message = message;
    }

    public EmailExistsException() {
        super();
    }   
    

   
}