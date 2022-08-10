package pe.galaxy.masterestfull.model;

import lombok.*;
import javax.persistence.*;


 // clase de entidad clase que se asignará con la tabla de base de datos MySQL.Uso de Lombok

@Entity
@Data
@AllArgsConstructor
@Table(name = "CargaCVS")
public class CargaCvs {


	@Id
	  @Column(name = "Id")
	  private long Id;

	  @Column(name = "Nombre")
	  private String Nombre;

	  @Column(name = "Apellido")
	  private String Apellido;

	  @Column(name = "Curso")
	  private String Curso;

	@Column(name = "Activo")
	private boolean Activo;


	public CargaCvs() {

	}
}
