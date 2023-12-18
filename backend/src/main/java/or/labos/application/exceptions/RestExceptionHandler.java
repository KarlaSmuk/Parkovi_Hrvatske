package or.labos.application.exceptions;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import or.labos.application.dto.requests.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> resolveException(EntityNotFoundException ex) {
        Response<Object> responseDto = new Response<>();
        responseDto.setStatus(HttpStatus.NOT_FOUND);
        responseDto.setMessage(ex.getMessage());
        responseDto.setResponse(null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public ResponseEntity<Object> resolveException(HttpRequestMethodNotSupportedException ex) {
        Response<Object> responseDto = new Response<>();
        responseDto.setStatus(HttpStatus.NOT_IMPLEMENTED);
        responseDto.setMessage(ex.getMessage());
        responseDto.setResponse(null);

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(responseDto);
    }

    @ExceptionHandler(EntityExistsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> resolveException(EntityExistsException ex) {
        Response<Object> responseDto = new Response<>();
        responseDto.setStatus(HttpStatus.CONFLICT);
        responseDto.setMessage(ex.getMessage());
        responseDto.setResponse(null);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> resolveException(MethodArgumentNotValidException ex) {

        Response<Object> responseDto = new Response<>();
        responseDto.setStatus(HttpStatus.BAD_REQUEST);
        responseDto.setMessage("Validation failed");
        responseDto.setResponse(null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<?> resolveException(Exception e) {

        Response<Object> responseDto = new Response<>();
        responseDto.setStatus(HttpStatus.BAD_REQUEST);
        responseDto.setMessage(e.getMessage());
        responseDto.setResponse(null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }

}

