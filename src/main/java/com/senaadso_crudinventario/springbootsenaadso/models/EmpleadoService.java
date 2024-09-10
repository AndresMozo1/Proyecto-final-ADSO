package com.senaadso_crudinventario.springbootsenaadso.models;

import com.senaadso_crudinventario.springbootsenaadso.services.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public List<Empleado> buscarEmpleados(String query) {
        return empleadoRepository.buscarPorNombreOApellido(query.toLowerCase());
    }

    public List<Empleado> obtenerTodosLosEmpleados() {
        return empleadoRepository.findAll();
    }

    public void guardarEmpleado(Empleado empleado) {
        empleadoRepository.save(empleado);
    }

    public void eliminarEmpleado(Long id) {
        empleadoRepository.deleteById(id);
    }

    public Optional<Empleado> obtenerEmpleadoPorId(Long id) {
        return empleadoRepository.findById(id);
    }
}

