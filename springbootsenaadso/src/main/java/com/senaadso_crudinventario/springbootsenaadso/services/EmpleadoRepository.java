package com.senaadso_crudinventario.springbootsenaadso.services;

import com.senaadso_crudinventario.springbootsenaadso.models.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    // Consultas personalizadas si es necesario
}

