package development.twomicroservices.handler;

import development.twomicroservices.dto.ErrorDto;
import development.twomicroservices.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(InvalidInputExpiredException.class)
    public ResponseEntity<?> handleInvalidInputExpiredException(InvalidInputExpiredException ex) {
        ErrorDto errorResponse = new ErrorDto(ex.getMessage(), 400);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(InvalidInputSignatureException.class)
    public ResponseEntity<ErrorDto> handleInvalidInputSignatureException(InvalidInputSignatureException ex) {
        ErrorDto errorResponse = new ErrorDto(ex.getMessage(), 500);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(InvalidInputTokenException.class)
    public ResponseEntity<ErrorDto> handleInvalidInputTokenException(InvalidInputTokenException ex) {
        ErrorDto errorResponse = new ErrorDto(ex.getMessage(), 500);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(InvalidRoleNotFound.class)
    public ResponseEntity<ErrorDto> handleInvalidRoleNotFound(InvalidRoleNotFound ex) {
        ErrorDto errorResponse = new ErrorDto(ex.getMessage(), 400);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(InvalidUserAlreadyExists.class)
    public ResponseEntity<ErrorDto> handleInvalidUserAlreadyExists(InvalidUserAlreadyExists ex) {
        ErrorDto errorResponse = new ErrorDto(ex.getMessage(), 400);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(InvalidUserNameOrPassword.class)
    public ResponseEntity<ErrorDto> handleInvalidUserNameOrPassword(InvalidUserNameOrPassword ex) {
        ErrorDto errorResponse = new ErrorDto(ex.getMessage(), 400);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(InvalidUserNotFound.class)
    public ResponseEntity<ErrorDto> handleInvalidUserNotFound(InvalidUserNotFound ex) {
        ErrorDto errorResponse = new ErrorDto(ex.getMessage(), 400);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<ErrorDto> handleTokenNotFoundException(TokenNotFoundException ex) {
        ErrorDto errorResponse = new ErrorDto(ex.getMessage(), 400);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(HavingRole.class)
    public ResponseEntity<ErrorDto> handleHavingRole(HavingRole ex) {
        ErrorDto errorResponse = new ErrorDto(ex.getMessage(), 400);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}