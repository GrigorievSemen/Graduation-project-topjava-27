package ru.grigoriev.graduationproject.web.user.handler;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.grigoriev.graduationproject.exception.NotFoundException;
import ru.grigoriev.graduationproject.exception.VotingErrorException;
import ru.grigoriev.graduationproject.web.user.response.BaseWebResponse;

import javax.validation.ConstraintViolationException;
import java.sql.SQLException;


@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<BaseWebResponse> handleNotFoundExceptionException(@NonNull final NotFoundException exc) {
        log.error(exc.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseWebResponse(createErrorMessage(exc)));
    }

    @ExceptionHandler(VotingErrorException.class)
    public ResponseEntity<BaseWebResponse> handleNotFoundExceptionException(@NonNull final VotingErrorException exc) {
        log.error(exc.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseWebResponse(createErrorMessage(exc)));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BaseWebResponse> handleConstraintViolationExceptionException(@NonNull final ConstraintViolationException exc) {
        log.error(exc.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseWebResponse(createErrorMessage(exc)));
    }

    @ExceptionHandler(PropertyValueException.class)
    public ResponseEntity<BaseWebResponse> handlePropertyValueException(@NonNull final PropertyValueException exc) {
        log.error(exc.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseWebResponse(createErrorMessage(exc)));
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<BaseWebResponse> handleSQLException(@NonNull final SQLException exc) {
        log.error(exc.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseWebResponse(createErrorMessage(exc)));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<BaseWebResponse> handleBadCredentialsException(@NonNull final BadCredentialsException exc) {
        log.error(exc.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseWebResponse(createErrorMessage(exc)));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<String> handleBindingErrors(BindException e) {
        return new ResponseEntity<>(e.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }

    private String createErrorMessage(Exception exception) {
        final String message = ExceptionHandlerUtils.buildErrorMessage(exception);
        log.error(message);
        return message;
    }
}
