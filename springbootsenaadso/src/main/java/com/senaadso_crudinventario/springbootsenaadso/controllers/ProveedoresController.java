package com.senaadso_crudinventario.springbootsenaadso.controllers;

import com.senaadso_crudinventario.springbootsenaadso.models.Proveedor;
import com.senaadso_crudinventario.springbootsenaadso.services.ProveedoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/proveedores")
public class ProveedoresController {

    @Autowired
    private ProveedoresRepository proveedoresRepository;

    // Mostrar lista de proveedores
    @GetMapping
    public String listarProveedores(Model model) {
        List<Proveedor> proveedores = proveedoresRepository.findAll(); // Usar findAll() de JpaRepository
        model.addAttribute("proveedores", proveedores);
        return "proveedores/listarProveedores";
    }

    // Mostrar formulario para crear un nuevo proveedor
    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("proveedor", new Proveedor());
        return "proveedores/crearEditarProveedor";
    }

    // Guardar un nuevo proveedor
    @PostMapping("/guardar")
    public String guardarProveedor(@Valid @ModelAttribute("proveedor") Proveedor proveedor, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "proveedores/crearEditarProveedor";
        }
        proveedoresRepository.save(proveedor); // Usar save() de JpaRepository
        redirectAttributes.addFlashAttribute("mensaje", "Proveedor creado con éxito.");
        return "redirect:/proveedores";
    }

    // Mostrar formulario para editar un proveedor existente
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        Proveedor proveedor = proveedoresRepository.findById(id).orElse(null); // Usar findById() de JpaRepository
        if (proveedor == null) {
            redirectAttributes.addFlashAttribute("error", "Proveedor no encontrado.");
            return "redirect:/proveedores";
        }
        model.addAttribute("proveedor", proveedor);
        return "proveedores/crearEditarProveedor";
    }

    // Actualizar un proveedor existente
    @PostMapping("/actualizar/{id}")
    public String actualizarProveedor(@PathVariable int id, @Valid @ModelAttribute("proveedor") Proveedor proveedor, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "proveedores/crearEditarProveedor";
        }
        Proveedor proveedorExistente = proveedoresRepository.findById(id).orElse(null);
        if (proveedorExistente == null) {
            redirectAttributes.addFlashAttribute("error", "Proveedor no encontrado.");
            return "redirect:/proveedores";
        }
        proveedorExistente.setNombre(proveedor.getNombre());
        proveedorExistente.setNit(proveedor.getNit());
        proveedorExistente.setTelefono(proveedor.getTelefono());
        proveedorExistente.setDireccion(proveedor.getDireccion());

        proveedoresRepository.save(proveedorExistente); // Usar save() de JpaRepository
        redirectAttributes.addFlashAttribute("mensaje", "Proveedor actualizado con éxito.");
        return "redirect:/proveedores";
    }

    // Eliminar un proveedor
    @GetMapping("/eliminar/{id}")
    public String eliminarProveedor(@PathVariable int id, RedirectAttributes redirectAttributes) {
        proveedoresRepository.deleteById(id); // Usar deleteById() de JpaRepository
        redirectAttributes.addFlashAttribute("mensaje", "Proveedor eliminado con éxito.");
        return "redirect:/proveedores";
    }
}