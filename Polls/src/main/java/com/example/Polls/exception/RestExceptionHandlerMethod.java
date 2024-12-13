package com.example.Polls.exception;

import com.example.Polls.Error.ErrorDetail;
import com.example.Polls.Error.ValidationError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandlerMethod {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationError(MethodArgumentNotValidException manve, HttpServletRequest request) {
        ErrorDetail errorDetails = new ErrorDetail();
        errorDetails.setTimeStamp(new Date().getTime());
        errorDetails.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDetails.setTitle("Validation Failed");
        errorDetails.setDetail("Input validation failed");
        errorDetails.setDeveloperMessage(manve.getClass().getName());

        List<FieldError> fieldErrors = manve.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            List<ValidationError> validationErrorList = errorDetails.getErrors().get(fieldError.getField());
            if (validationErrorList == null) {
                validationErrorList = new ArrayList<>();
                errorDetails.getErrors().put(fieldError.getField(), validationErrorList);
            }
            ValidationError validationError = new ValidationError();
            validationError.setCode(fieldError.getCode());
            validationError.setMessage(fieldError.getDefaultMessage());
            validationErrorList.add(validationError);
        }
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
