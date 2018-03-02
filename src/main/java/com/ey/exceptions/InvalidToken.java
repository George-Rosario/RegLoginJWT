package com.ey.exceptions;

/**
 * @author George.Rosario
 *
 */
public class InvalidToken extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String messages;

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public InvalidToken(String messages) {
        super();
        this.messages = messages;
    }
    
    public InvalidToken() {
        super();
    }
    

}