package ua.ivan909020.scheduler.rest.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ua.ivan909020.scheduler.rest.exception.EntityValidationException;
import ua.ivan909020.scheduler.rest.model.dto.ErrorCode;
import ua.ivan909020.scheduler.rest.model.dto.ErrorResponseDto;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityValidationException.class)
    public ResponseEntity<ErrorResponseDto> handleEntityValidationException(EntityValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto(ErrorCode.NOT_VALID_BODY, ex.getMessage()));
    }

}
