package sale.ljw.clinic_administrator_background_system.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler
    @ResponseBody
    public String handleException(Exception e) {
        return "";
    }
}
