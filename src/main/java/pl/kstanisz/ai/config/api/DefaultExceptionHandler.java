package pl.kstanisz.ai.config.api;

import dev.langchain4j.guardrail.InputGuardrailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
class DefaultExceptionHandler {

    @ExceptionHandler(InputGuardrailException.class)
    public ResponseEntity<Object> handleInputGuardrailException() {
        return ResponseEntity.unprocessableEntity().build();
    }

}
