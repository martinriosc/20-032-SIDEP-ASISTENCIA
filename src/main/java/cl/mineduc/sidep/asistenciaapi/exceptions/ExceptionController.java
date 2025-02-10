package cl.mineduc.sidep.asistenciaapi.exceptions;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ResponseBody
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {

    @ExceptionHandler(value = SostenedorException.class)
    protected ResponseEntity<Map<String, Object>> handleSostenedorException(SostenedorException e, HttpServletRequest request) {

        return getMapResponseEntity(request, e);
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    protected ResponseEntity<Map<String, Object>> handleMissingServletRequestParameterException(MissingServletRequestParameterException e, HttpServletRequest request) {
        return getMapResponseEntity(request, e);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    protected ResponseEntity<Map<String, Object>> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        ResponseEntity<Map<String, Object>> response = getMapResponseEntity(request, e);
        Map<String, Object> map = response.getBody();

        if (map == null) {
            map = new HashMap<>();
        }

        Map<String, Object> finalMap = map;
        Map<String, String> errors = new HashMap<>();

        finalMap.remove("error");

        e.getBindingResult()
                .getAllErrors()
                .forEach(error -> {
                    String fieldName = error.getObjectName();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });

        finalMap.put("error", errors);

        return ResponseEntity.badRequest().body(finalMap);
    }

    private ResponseEntity<Map<String, Object>> getMapResponseEntity(HttpServletRequest request, Exception e) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", e.getMessage());
        response.put("timestamp", LocalDateTime.now());
        response.put("uri", request.getRequestURI());

        return ResponseEntity.badRequest().body(response);
    }

}
