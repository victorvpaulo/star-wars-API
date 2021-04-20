package dev.victor.paulo.startwarsAPI.web.exceptionhandler;

import dev.victor.paulo.startwarsAPI.service.exception.PlanetNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class PlanetControllersAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PlanetNotFoundException.class)
    public ResponseEntity<Object> handlePlanetNotFoundException(PlanetNotFoundException ex, WebRequest request) {

        return ResponseEntity
                .notFound()
                .build();
    }
}
