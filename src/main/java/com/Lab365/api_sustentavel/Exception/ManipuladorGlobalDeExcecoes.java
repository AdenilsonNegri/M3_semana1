package com.Lab365.api_sustentavel.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ManipuladorGlobalDeExcecoes {

  //  Trata: Validação de campos (@Valid)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> tratarValidacao(MethodArgumentNotValidException ex) {
    Map<String, Object> corpo = new HashMap<>();
    corpo.put("timestamp", LocalDateTime.now());
    corpo.put("status", HttpStatus.BAD_REQUEST.value());
    corpo.put("erro", "Erro de validação");
    corpo.put("mensagem", "Campos inválidos");

    // Detalhes dos campos com erro
    Map<String, String> erros = new HashMap<>();
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      erros.put(error.getField(), error.getDefaultMessage());
    }
    corpo.put("detalhes", erros);

    return ResponseEntity.badRequest().body(corpo);
  }

  //  Trata: Recurso não encontrado (ex: ID inexistente)
  @ExceptionHandler(RecursoNaoEncontradoException.class)
  public ResponseEntity<Map<String, Object>> tratarRecursoNaoEncontrado(RecursoNaoEncontradoException ex) {
    Map<String, Object> corpo = new HashMap<>();
    corpo.put("timestamp", LocalDateTime.now());
    corpo.put("status", HttpStatus.NOT_FOUND.value());
    corpo.put("erro", "Recurso não encontrado");
    corpo.put("mensagem", ex.getMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(corpo);
  }

  //  Trata: Qualquer outra exceção não esperada (fallback)
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> tratarErroInterno(Exception ex) {
    Map<String, Object> corpo = new HashMap<>();
    corpo.put("timestamp", LocalDateTime.now());
    corpo.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
    corpo.put("erro", "Erro interno do servidor");
    corpo.put("mensagem", "Ocorreu um erro inesperado. Tente novamente mais tarde.");
    // opcional: logar o ex.printStackTrace() aqui (não exibir para o cliente)

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(corpo);
  }
}
