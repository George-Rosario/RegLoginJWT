package com.ey.exceptions;

/**
 * @author George.Rosario
 *
 */
public class TokenExpiredException extends Exception {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String messages;
   
    public String getMessages() {
        return messages;
    }
    public TokenExpiredException(String messages) {
        super();
        this.messages = messages;
    }
    public void setMessages(String messages) {
        this.messages = messages;
    }
    
    public TokenExpiredException() {
        super();
    }
    
    
    

}