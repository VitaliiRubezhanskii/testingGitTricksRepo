package springajax.exceptionhandler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import springajax.controller.RestBookController;
import springajax.exception.BookNotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.logging.Logger;

@ControllerAdvice
public class BookRestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = Logger.getLogger(BookRestResponseEntityExceptionHandler.class.getName());

    public BookRestResponseEntityExceptionHandler() {
    }


    @ExceptionHandler({BookNotFoundException.class})
    public ResponseEntity<Object> handleBadRequest(final BookNotFoundException ex, final WebRequest request){
        String message="This is application specific";
        logger.info("message");
        return  handleExceptionInternal(ex,message,new HttpHeaders(),HttpStatus.BAD_REQUEST,request);


    }
}
