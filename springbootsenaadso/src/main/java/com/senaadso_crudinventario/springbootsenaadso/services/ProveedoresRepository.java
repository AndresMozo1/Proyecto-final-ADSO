package com.senaadso_crudinventario.springbootsenaadso.services;



import com.senaadso_crudinventario.springbootsenaadso.models.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ProveedoresRepository extends JpaRepository<Proveedor, Integer> {

    // No es necesario definir obtenerTodosLosProveedores() ya que JpaRepository lo proporciona
    // List<Proveedor> findAll(); // Este método ya está disponible

    // No es necesario definir obtenerProveedorPorId() ya que JpaRepository lo proporciona
    // Optional<Proveedor> findById(Integer id); // Este método ya está disponible

    
}