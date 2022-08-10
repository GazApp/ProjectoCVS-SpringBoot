package pe.galaxy.masterestfull.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class FileUploadExceptionAdvice extends ResponseEntityExceptionHandler {

  @SuppressWarnings("rawtypes")
@ExceptionHandler(MaxUploadSizeExceededException.class)
  public ResponseEntity handleMaxSizeException(MaxUploadSizeExceededException exc) {
    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("Archivo Demasido Grande!",""));
  }
}

/*
Clase para controlar la excepción de carga de archivos.

En esta clase, definiremos el método que es responsable de manejar la excepción que surge cuando alguien
intenta cargar el archivo de tamaño más que el valor configurado
en el archivo application.properties.
 */