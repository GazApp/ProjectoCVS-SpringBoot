package pe.galaxy.masterestfull.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pe.galaxy.masterestfull.service.CSVService;
import pe.galaxy.masterestfull.exception.ResponseMessage;
import pe.galaxy.masterestfull.helper.CSVHelper;
import pe.galaxy.masterestfull.model.CargaCvs;

/*
clase Controller.

En este paso donde definiremos todos los endpoints o API para subir, descargar o leer, escribir los datos del Archivo CSV.
 */

@CrossOrigin("http://localhost:8080")
@Controller
@RequestMapping("/api/csv")
public class CSVController {

  @Autowired
  CSVService fileService;

  @PostMapping("/upload")
  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
    String message = "";

    if (CSVHelper.hasCSVFormat(file)) {
      try {
        fileService.save(file);

        message = "Carga de archivo CVS con exito !!!: " + file.getOriginalFilename();
        
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/csv/download/")
                .path(file.getOriginalFilename())
                .toUriString();

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,fileDownloadUri));
      } catch (Exception e) {
        message = "No se pudo cargar el archivo CVS " + file.getOriginalFilename() + "!";
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message,""));
      }
    }

    message = "Por favor cargue el archivo CVS!";
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message,""));
  }

  @GetMapping("/tutorials")
  public ResponseEntity<List<CargaCvs>> getAllData() {
    try {
      List<CargaCvs> tutorials = fileService.getAllData();

      if (tutorials.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(tutorials, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/download/{fileName:.+}")
  public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
    InputStreamResource file = new InputStreamResource(fileService.load());

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
        .contentType(MediaType.parseMediaType("application/csv"))
        .body(file);
  }
}

/*
Anotación utilizada.

@CrossOrigin: Esta anotación se utiliza para configurar los orígenes a los que se les permite llamar a nuestra API.
@Controller: Para hacer la clase como Controller.
@RequestMapping: Esta anotación se utiliza para asignar los métodos de clase específicos cuando una determinada API recibe una llamada del cliente.
@GetMapping: Para controlar la solicitud HTTP GET.
@PostMapping: Se utiliza para manejar la solicitud HTTP POST.
Métodos utilizados en la clase de controlador.

Hay un total de 3 métodos definidos en la clase de controlador que se mapean con tres API diferentes.

uploadFile(): Se utiliza para cargar el archivo.
getAllTutorials(): Para obtener toda la lista de tutoriales para desarrolladores de la base de datos.
downloadFile(): Para descargar el archivo CSV.
 */