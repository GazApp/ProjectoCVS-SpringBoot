package pe.galaxy.masterestfull.helper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.web.multipart.MultipartFile;
import pe.galaxy.masterestfull.model.CargaCvs;

/*
 clase para leer y escribir el archivo CSV
 se usa una dependencia en pom.xml más llamada Apache Commons CSV.
 Proporciona  clases incorporadas como: CSVParser, CSVRecord, CSVFormat para leer y escribir los datos de CSV File.
 */
public class CSVHelper {
  public static String TYPE = "text/csv";
  static String[] HEADERs = { "Id", "Nombre", "Apellido", "Curso", "Activo" };

  //Se utiliza para comprobar que el formato de archivo es CSV o no.
  public static boolean hasCSVFormat(MultipartFile file) {
 System.out.println(file.getContentType());
    if (TYPE.equals(file.getContentType())
    		|| file.getContentType().equals("application/vnd.ms-excel")) {
      return true;
    }

    return false;
  }
//Este método se utiliza completamente para leer los datos del archivo CSV.
// A continuación tengo resúmenes de cada línea de código utilizada en este método.
//Para leer el archivo CSV.
//se crea un objeto BufferedReader a partir de InputStream


  //  A continuación, estamos creando un objeto CSVParser a partir de BufferedReader y InputStream.
  public static List<CargaCvs> csvToStream(InputStream is) {
    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        CSVParser csvParser = new CSVParser(fileReader,
            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

      List<CargaCvs> listCvs = new ArrayList<>();

//Con la ayuda de la referencia de objeto CSVParser, estamos llamando a un método predefinido llamado getRecords(),
// que devuelve el contenido presente en el archivo CSV en forma de registro.

      Iterable<CSVRecord> csvRecords = csvParser.getRecords();


      //Luego estamos iterando sobre cada registro y con la ayuda del método get(), estamos obteniendo el valor de cada campo
      for (CSVRecord csvRecord : csvRecords) {
    	  CargaCvs cargaCvs = new CargaCvs(
              Long.parseLong(csvRecord.get("Id")),
              csvRecord.get("Nombre"),
              csvRecord.get("Apellido"),
                  csvRecord.get("Curso"),
                  Boolean.parseBoolean(csvRecord.get("Activo"))
            );

          listCvs.add(cargaCvs);
      }

      return listCvs;
    } catch (IOException e) {
      throw new RuntimeException("Falla en el parse del CVS: " + e.getMessage());
    }
  }
//Este método se utiliza para escribir los datos en el archivo CSV desde la tabla de la base de datos MySQL.Para escribir el archivo CSV
  public static ByteArrayInputStream inToCSV(List<CargaCvs> cargaCvsList) {
    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
        //A continuación, estamos iterando sobre la lista de objetos inToCSV y almacenando todos y cada uno de los valores en forma de una lista de cadenas, y luego,
        // llamamos al método csvPrinter.printRecord()
      for (CargaCvs cargaCvs : cargaCvsList) {
        List<String> data = Arrays.asList(
              String.valueOf(cargaCvs.getId()),
              cargaCvs.getNombre(),
              cargaCvs.getApellido(),
                cargaCvs.getCurso(),
                String.valueOf(cargaCvs.isActivo())
            );
//Creación de un objeto CSVPrinter para imprimir el valor en un archivo CSV.
        csvPrinter.printRecord(data);
      }

      csvPrinter.flush();
      //En el último paso, estamos eliminando todos los caracteres o datos de flujo en el archivo CSV final con el uso del método a continuación
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException("Falla en la importaccion del archivo CVS: " + e.getMessage());
    }
  }
}
