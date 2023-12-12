package or.labos.application.exceptions;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import or.labos.application.dto.ResponseDto;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> resolveException(EntityNotFoundException ex) {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        responseDto.setStatus(HttpStatus.NOT_FOUND);
        responseDto.setMessage(ex.getMessage());
        responseDto.setResponse(null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public ResponseEntity<Object> resolveException(HttpRequestMethodNotSupportedException ex) {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        responseDto.setStatus(HttpStatus.NOT_IMPLEMENTED);
        responseDto.setMessage(ex.getMessage());
        responseDto.setResponse(null);

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(responseDto);
    }

    @ExceptionHandler(EntityExistsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> resolveException(EntityExistsException ex) {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        responseDto.setStatus(HttpStatus.CONFLICT);
        responseDto.setMessage(ex.getMessage());
        responseDto.setResponse(null);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public ResponseEntity<Object> resolveException(MethodArgumentNotValidException ex) {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        responseDto.setStatus(HttpStatus.NOT_IMPLEMENTED);
        responseDto.setMessage(ex.getMessage());
        responseDto.setResponse(null);

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(responseDto);
    }

}
