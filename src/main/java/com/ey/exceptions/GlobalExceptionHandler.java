package com.ey.exceptions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.sun.mail.util.MailConnectException;

/**
 * @author George.Rosario
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<ExceptionEntity> handleConstraintViolation(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        ExceptionEntity exceptionEntity = new ExceptionEntity();

        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            exceptionEntity.setMessage(constraintViolation.getMessage());
            exceptionEntity.setTimestamp(LocalDateTime.now());
            exceptionEntity.setErrorcode(HttpStatus.BAD_REQUEST);
            ArrayList<ExceptionEntitySubError> list = new ArrayList<>();
            ExceptionEntitySubError exceptionEntitySubError = new ExceptionEntitySubError();
            exceptionEntitySubError.setClassName(constraintViolation.getRootBeanClass().toString());
            exceptionEntitySubError.setCompleteDicription(constraintViolation.getConstraintDescriptor().toString());
            exceptionEntitySubError.setRejectedValue(constraintViolation.getInvalidValue().toString());
            exceptionEntitySubError.setField(constraintViolation.getPropertyPath().toString());
            list.add(exceptionEntitySubError);

            exceptionEntity.setSubErrors(list);
        }

        /*
         * Set<String> messages = new HashSet<>(constraintViolations.size());
         * messages.addAll(constraintViolations.stream()
         * .map(constraintViolation -> String.format("%s value '%s' %s",
         * constraintViolation.getPropertyPath(),
         * constraintViolation.getInvalidValue(),
         * constraintViolation.getMessage())) .collect(Collectors.toList()));
         */

        return new ResponseEntity<ExceptionEntity>(exceptionEntity, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<ExceptionEntity> emailNotfound(EmailExistsException e) {
        ExceptionEntity exceptionEntity = new ExceptionEntity();
        exceptionEntity.setErrorcode(HttpStatus.CONFLICT);
        exceptionEntity.setMessage(e.getMessage());
        exceptionEntity.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<ExceptionEntity>(exceptionEntity, HttpStatus.CONFLICT);

    }

    @ExceptionHandler(RegistrationInvalidToken.class)
    public ResponseEntity<ExceptionEntity> registrationInvalidTokenSentException(RegistrationInvalidToken e) {
        ExceptionEntity exceptionEntity = new ExceptionEntity();
        exceptionEntity.setErrorcode(HttpStatus.UNAUTHORIZED);
        exceptionEntity.setMessage(e.getMessages());
        exceptionEntity.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<ExceptionEntity>(exceptionEntity, HttpStatus.UNAUTHORIZED);

    }

    @ExceptionHandler(RegistrationTokenExpiredException.class)
    public ResponseEntity<ExceptionEntity> registraionTokenExpiredException(RegistrationTokenExpiredException e) {
        ExceptionEntity exceptionEntity = new ExceptionEntity();
        exceptionEntity.setErrorcode(HttpStatus.UNAUTHORIZED);
        exceptionEntity.setMessage(e.getMessages());
        exceptionEntity.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<ExceptionEntity>(exceptionEntity, HttpStatus.UNAUTHORIZED);

    }

    @ExceptionHandler(MailSendException.class)
    public ResponseEntity<ExceptionEntity> socketTimeOutException(MailSendException e) {
        ExceptionEntity exceptionEntity = new ExceptionEntity();
        Map<Object, Exception> map = e.getFailedMessages();
        Exception[] exarray = e.getMessageExceptions();
        ArrayList<ExceptionEntitySubError> exceptionentitySubErrorList = new ArrayList<ExceptionEntitySubError>();
        for (Exception exception : exarray) {
            MailConnectException mailex = (MailConnectException) exception;
            ExceptionEntitySubError exceptionentitySubError = new ExceptionEntitySubError();
            exceptionentitySubError.setClassName(mailex.getCause().toString());
            exceptionentitySubError.setCompleteDicription("HOST:" + mailex.getHost() + " PORT:" + mailex.getPort()
                + " Connection Time Out In" + mailex.getConnectionTimeout());
            exceptionentitySubError.setCause(((Throwable) mailex.getCause()).getMessage());
            exceptionentitySubErrorList.add(exceptionentitySubError);
        }
        exceptionEntity.setErrorcode(HttpStatus.REQUEST_TIMEOUT);
        exceptionEntity.setMessage("Connection Time Out for SMTP");
        exceptionEntity.setTimestamp(LocalDateTime.now());
        exceptionEntity.setSubErrors(exceptionentitySubErrorList);
        return new ResponseEntity<ExceptionEntity>(exceptionEntity, HttpStatus.REQUEST_TIMEOUT);

    }

}