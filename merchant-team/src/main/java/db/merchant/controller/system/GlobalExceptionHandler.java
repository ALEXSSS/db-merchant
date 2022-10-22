package db.merchant.controller.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

/**
 * FUTURE WORK: create team-wide hierarchy of exceptions and map them on
 * response instead of default spring exceptions
 */
@ControllerAdvice
class GlobalExceptionHandler implements Ordered {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception exp) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = exp.getMessage() == null ? "Unknown error" : exp.getMessage();
        if (exp instanceof ResponseStatusException) {
            ResponseStatusException castedExp = (ResponseStatusException) exp;
            status = castedExp.getStatus();
            message = castedExp.getMessage();
        } else {
            log.error("Not handled exception", exp);
        }
        return new ResponseEntity<>(message, status);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
}
