package com.senaadso_crudinventario.springbootsenaadso.services;

import com.senaadso_crudinventario.springbootsenaadso.models.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProveedoresRepository extends JpaRepository<Proveedor, Integer> {
    List<Proveedor> findByNombreContainingOrNitContaining(String nombre, String nit);
}
