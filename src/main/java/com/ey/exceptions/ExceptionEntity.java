package com.ey.exceptions;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author George.Rosario
 *
 */
public class ExceptionEntity {
    
    private HttpStatus errorcode;
    private String message;
    private List<ExceptionEntitySubError> subErrors;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    
    
    public HttpStatus getErrorcode() {
        return errorcode;
    }
    public void setErrorcode(HttpStatus errorcode) {
        this.errorcode = errorcode;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
   
    public List<ExceptionEntitySubError> getSubErrors() {
        return subErrors;
    }
    public void setSubErrors(List<ExceptionEntitySubError> subErrors) {
        this.subErrors = subErrors;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

}