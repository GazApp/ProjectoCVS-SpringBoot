package pe.galaxy.masterestfull.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.galaxy.masterestfull.model.CargaCvs;

@Repository
public interface RepositoryCvs extends JpaRepository<CargaCvs, Integer>{

}

/*
Repositorio que se comunicará con nuestra base de datos y realizará todo tipo de Operación CRUD
En este paso extenderemos una clase predefinida llamada JpaRepository que proporciona todos los métodos posibles necesarios para crear,
eliminar, actualizar y obtener los datos de la tabla de base de datos.
@Repository: Esta anotación indica que la clase o interfaz está completamente dedicada a realizar
*/
