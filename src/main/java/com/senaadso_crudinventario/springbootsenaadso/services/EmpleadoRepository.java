package com.senaadso_crudinventario.springbootsenaadso.services;

import com.senaadso_crudinventario.springbootsenaadso.models.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    @Query("SELECT e FROM Empleado e WHERE LOWER(e.nombre) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(e.apellido) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Empleado> buscarPorNombreOApellido(@Param("query") String query);
}
