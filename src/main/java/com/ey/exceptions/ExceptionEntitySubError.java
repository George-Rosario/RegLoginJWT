package com.ey.exceptions;

/**
 * @author George.Rosario
 *
 */
public class ExceptionEntitySubError {
    
    private String field;
    private String rejectedValue;
    private String className;
    private String completeDicription;
    private String cause;
    public String getField() {
        return field;
    }
    public void setField(String field) {
        this.field = field;
    }
    public String getRejectedValue() {
        return rejectedValue;
    }
    public void setRejectedValue(String rejectedValue) {
        this.rejectedValue = rejectedValue;
    }
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public String getCompleteDicription() {
        return completeDicription;
    }
    public void setCompleteDicription(String completeDicription) {
        this.completeDicription = completeDicription;
    }
    public String getCause() {
        return cause;
    }
    public void setCause(String cause) {
        this.cause = cause;
    }
    

}