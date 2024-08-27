package com.senaadso_crudinventario.springbootsenaadso.services;

// Importa la interfaz JpaRepository de Spring Data JPA.
import org.springframework.data.jpa.repository.JpaRepository;
// Importa el modelo de entidad Producto.
import com.senaadso_crudinventario.springbootsenaadso.models.Producto;

// Define una interfaz ProductosRepository que extiende JpaRepository.
// JpaRepository proporciona métodos CRUD (Crear, Leer, Actualizar, Eliminar)
// y otras operaciones de base de datos para la entidad Producto.
// La interfaz JpaRepository requiere dos parámetros: 
// 1. El tipo de la entidad (Producto) 
// 2. El tipo de dato de la clave primaria (Integer).
public interface ProductosRepository extends JpaRepository<Producto, Integer> {

}

