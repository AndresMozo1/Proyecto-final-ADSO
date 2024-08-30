package com.senaadso_crudinventario.springbootsenaadso.controllers;

import com.senaadso_crudinventario.springbootsenaadso.models.Empleado;
import com.senaadso_crudinventario.springbootsenaadso.models.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping
    public String listarEmpleados(Model model) {
        List<Empleado> empleados = empleadoService.obtenerTodosLosEmpleados();
        model.addAttribute("empleados", empleados);
        return "empleados/listarEmpleados";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioDeCrear(Model model) {
        Empleado empleado = new Empleado();
        model.addAttribute("empleado", empleado);
        return "empleados/crearEditarEmpleado";
    }

    @PostMapping
    public String guardarEmpleado(@ModelAttribute("empleado") Empleado empleado) {
        empleadoService.guardarEmpleado(empleado);
        return "redirect:/empleados";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioDeEditar(@PathVariable Long id, Model model) {
        Empleado empleado = empleadoService.obtenerEmpleadoPorId(id)
                                           .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado con el ID: " + id));
        model.addAttribute("empleado", empleado);
        return "empleados/crearEditarEmpleado";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarEmpleado(@PathVariable Long id) {
        empleadoService.eliminarEmpleado(id);
        return "redirect:/empleados";
    }
}
