package or.labos.application.exceptions;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import or.labos.application.dto.requests.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    public ResponseEntity<Object> resolveException(EntityNotFoundException ex) {
        Response<Object> responseDto = new Response<>(HttpStatus.NOT_FOUND, ex.getMessage(), null);


        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseDto);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResponseEntity<Object> resolveException(HttpRequestMethodNotSupportedException ex) {
        Response<Object> responseDto = new Response<>(HttpStatus.NOT_IMPLEMENTED, ex.getMessage(), null);

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseDto);
    }

    @ExceptionHandler(EntityExistsException.class)
    @ResponseBody
    public ResponseEntity<Object> resolveException(EntityExistsException ex) {
        Response<Object> responseDto = new Response<>(HttpStatus.CONFLICT, ex.getMessage(), null);

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<Object> resolveException(MethodArgumentNotValidException ex) {

        Response<Object> responseDto = new Response<>(HttpStatus.BAD_REQUEST, "Validation failed", null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseDto);
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseBody
    protected ResponseEntity<?> resolveException(Exception e) {

        Response<Object> responseDto = new Response<>(HttpStatus.BAD_REQUEST, e.getMessage(), null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseDto);
    }

}

