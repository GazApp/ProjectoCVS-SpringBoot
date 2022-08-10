package pe.galaxy.masterestfull.exception;


/*
clase para el mensaje de respuesta.
En este paso, crearemos una clase para definir el formato del mensaje de respuesta de modo que cada vez que cualquier API o punto final reciba una llamada,
 devuelva la respuesta en el formato adecuado. Esta clase se usará en el siguiente paso al crear una clase de controlador.
 */
public class ResponseMessage {

	private String message;
	private String fileDownloadUri;
	

	  public ResponseMessage(String message, String fileDownloadUri) {
	    this.message = message;
	    this.fileDownloadUri = fileDownloadUri;
	  }


	public String getMessage() {
	    return message;
	  }

	  public void setMessage(String message) {
	    this.message = message;
	  }

	public String getFileDownloadUri() {
		return fileDownloadUri;
	}

	public void setFileDownloadUri(String fileDownloadUri) {
		this.fileDownloadUri = fileDownloadUri;
	}

	  
}
