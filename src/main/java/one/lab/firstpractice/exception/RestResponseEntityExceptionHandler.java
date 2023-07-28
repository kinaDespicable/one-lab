package one.lab.firstpractice.exception;

import one.lab.firstpractice.exception.exceptions.*;
import one.lab.firstpractice.model.dto.response.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException exception) {
        ErrorResponse errorResponse = constructResponse(exception, HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> resourceAlreadyExistExceptionHandler(ResourceAlreadyExistException exception) {
        ErrorResponse errorResponse = constructResponse(exception, HttpStatus.CONFLICT);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(EmptyAttributeValueException.class)
    public ResponseEntity<ErrorResponse> emptyAttributeValueExceptionHandler(EmptyAttributeValueException exception) {
        ErrorResponse errorResponse = constructResponse(exception, BAD_REQUEST);
        return ResponseEntity.status(BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ErrorResponse> passwordMismatchExceptionHandler(PasswordMismatchException exception) {
        ErrorResponse errorResponse = constructResponse(exception, BAD_REQUEST);
        return ResponseEntity.status(BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(NoAuthorityException.class)
    public ResponseEntity<ErrorResponse> noAuthorityExceptionHandler(NoAuthorityException exception) {
        ErrorResponse errorResponse = constructResponse(exception, BAD_REQUEST);
        return ResponseEntity.status(BAD_REQUEST).body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("status", BAD_REQUEST);
        responseBody.put("status_code", BAD_REQUEST.value());
        responseBody.put("timestamp", Instant.now());

        Map<String, Object> validationErrors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage()));

        responseBody.put("validation_errors", validationErrors);

        return ResponseEntity.status(BAD_REQUEST).body(responseBody);
    }


    private ErrorResponse constructResponse(Exception exception, HttpStatus httpStatus) {
        return ErrorResponse.builder()
                .status(httpStatus)
                .statusCode(httpStatus.value())
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .build();
    }

}
