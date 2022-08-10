package pe.galaxy.masterestfull.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pe.galaxy.masterestfull.repository.RepositoryCvs;
import pe.galaxy.masterestfull.helper.CSVHelper;
import pe.galaxy.masterestfull.model.CargaCvs;
/*
En esta clase vamos a definir los tres métodos de la siguiente manera.

save(Archivo MultipartFile): Para guardar los datos del archivo CSV en la base de datos.
load(): Leerá los datos de la base de datos y los devolverá en forma de ByteArrayInputStream.
getAllData(): Este método también leerá los datos de la base de datos y devolverá la Lista.
 */
@Service
public class CSVService {
  @Autowired
  RepositoryCvs repository;

  public void save(MultipartFile file) {
    try {
      List<CargaCvs> tutorials = CSVHelper.csvToStream(file.getInputStream());
      repository.saveAll(tutorials);
    } catch (IOException e) {
      throw new RuntimeException("Falla en la data del CVS: " + e.getMessage());
    }
  }

  public ByteArrayInputStream load() {
    List<CargaCvs> tutorials = repository.findAll();

    ByteArrayInputStream in = CSVHelper.inToCSV(tutorials);
    return in;
  }

  public List<CargaCvs> getAllData() {
    return repository.findAll();
  }
}

