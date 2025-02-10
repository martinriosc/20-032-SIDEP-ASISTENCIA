package cl.mineduc.sidep.asistenciaapi.exceptions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import javax.servlet.http.HttpServletRequest;

import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class ExceptionControllerTest {

    @InjectMocks
    private ExceptionController controller;

    private HttpServletRequest request;

    @Before
    public void setUp() {
        request = mock(HttpServletRequest.class);
        when(request.getMethod()).thenReturn("GET");
        when(request.getServletPath()).thenReturn("/");
        when(request.getRequestURI()).thenReturn("/");
    }

    @Test
    public void handleSostenedorException() {
        SostenedorException ex = new SostenedorException("error", new Throwable());
        assertNotNull(controller.handleSostenedorException(ex, request));
    }

    @Test
    public void handleMissingServletRequestParameterException() {
        MissingServletRequestParameterException ex = new MissingServletRequestParameterException("test", "error test");
        assertNotNull(controller.handleMissingServletRequestParameterException(ex, request));
    }

    @Test
    public void handlerMethodArgumentNotValidException() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);

        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(Collections.singletonList(new ObjectError("error", "error")));

        assertNotNull(controller.handlerMethodArgumentNotValidException(ex, request));
    }
}
