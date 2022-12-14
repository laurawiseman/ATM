package det.atm;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class TransactionTypeErrorAdvice {
    
    @ResponseBody
    @ExceptionHandler(TransactionTypeError.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String transactionTypeErrorHandler(TransactionTypeError ex) {
        return ex.getMessage();
    }
}
