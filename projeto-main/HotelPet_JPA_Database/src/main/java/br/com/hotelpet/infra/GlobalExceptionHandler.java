package br.com.hotelpet.infra;
import br.com.hotelpet.infra.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<String> erro404(EntidadeNaoEncontradaException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }
    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<String> erro400(ValidacaoException ex) {
        return ResponseEntity.status(400).body(ex.getMessage());
    }
}