package com.senaadso_crudinventario.springbootsenaadso.controllers;

import java.io.InputStream;
import java.nio.file.*;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.senaadso_crudinventario.springbootsenaadso.models.Producto;
import com.senaadso_crudinventario.springbootsenaadso.models.ProductoDto;
import com.senaadso_crudinventario.springbootsenaadso.services.ProductosRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/productos")
public class ProductosController {

	@Autowired
	private ProductosRepository repo;

	@GetMapping({ "", "/" })
	public String mostrarListaProductos(Model model) {
		List<Producto> productos = repo.findAll(Sort.by(Sort.Direction.DESC, "Id"));
		model.addAttribute("productos", productos);
		return "productos/index";
	}

	@GetMapping("/crear")
	public String showCreatePage(Model model) {
		ProductoDto productoDto = new ProductoDto();
		model.addAttribute("productoDto", productoDto);
		return "productos/crearproducto";
	}

	@PostMapping("/crear")
	public String crearProducto(@Valid @ModelAttribute ProductoDto productoDto, BindingResult resultado) {
		if (productoDto.getArchivoImagen().isEmpty()) {
			resultado.addError(
					new FieldError("productoDto", "archivoImagen", "El archivo para la imagen es obligatorio"));
		}

		if (resultado.hasErrors()) {
			return "productos/CrearProducto";
		}

		MultipartFile image = productoDto.getArchivoImagen();
		
		Date fechaCreacion = new Date(System.currentTimeMillis());

		String storageFileName = fechaCreacion.getTime() + "_" + image.getOriginalFilename();

		try {
			String uploadDir = "public/images/";
			Path uploadPath = Paths.get(uploadDir);

			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			try (InputStream inputStream = image.getInputStream()) {
				Files.copy(inputStream, uploadPath.resolve(storageFileName), StandardCopyOption.REPLACE_EXISTING);
			}

		} catch (Exception ex) {
			System.out.println("Excepción al grabar la imagen: " + ex.getLocalizedMessage());
		}

		// Registro en la base de datos del nuevo producto.
		Producto prod = new Producto();
		prod.setNombre(productoDto.getNombre());
		prod.setMarca(productoDto.getMarca());
		prod.setCategoria(productoDto.getCategoria());
		prod.setPrecio(productoDto.getPrecio());
		prod.setDescripcion(productoDto.getDescripcion());
		prod.setFechaCreacion(fechaCreacion);
		prod.setNombreArchivoImagen(storageFileName);

		repo.save(prod);

		return "redirect:/productos";
	}

	@GetMapping("/editar")
	public String showEditPage(Model model, @RequestParam int id) {
		try {
			Producto prod = repo.findById(id).get();
			model.addAttribute("producto", prod);

			ProductoDto productoDto = new ProductoDto();
			productoDto.setNombre(prod.getNombre());
			productoDto.setMarca(prod.getMarca());
			productoDto.setCategoria(prod.getCategoria());
			productoDto.setPrecio(prod.getPrecio());
			productoDto.setDescripcion(prod.getDescripcion());

			model.addAttribute("productoDto", productoDto);

		} catch (Exception ex) {
			System.out.println("Excepción al editar: " + ex.getMessage());
			return "redirect:/productos";
		}

		return "productos/EditarProducto";
	}

	@PostMapping("editar")
	public String actualizarProducto(Model model, @RequestParam int id,
			@Valid@ModelAttribute ProductoDto productoDto,
			BindingResult resultado) {


		try {
			Producto producto = repo.findById(id).get();
			model.addAttribute("Producto", producto);
			// Si  hay errores
			if(resultado.hasErrors()) {
				return "productos/Editarproducto";
			}

			if(!productoDto.getArchivoImagen().isEmpty()) {
				// Eliminamos la imagen antigua
				String dirDeImagenes="public/images/";
				Path rutaAntiguaImagen = Paths.get(dirDeImagenes + producto.getNombreArchivoImagen() );
				try {
					Files.delete(rutaAntiguaImagen);	  
				}catch(Exception ex) {
					System.out.println("Excepción: " + ex.getMessage());
				}

				//Grabar el archivo de la nueva imagen
				MultipartFile image = productoDto.getArchivoImagen();
				Date fechaCreacion = new Date(System.currentTimeMillis());
				String storageFileName = fechaCreacion.getTime() + "_" + image.getOriginalFilename();

				try(InputStream inputStream = image.getInputStream()) {
					Files.copy(inputStream, Paths.get(dirDeImagenes + storageFileName),
							StandardCopyOption.REPLACE_EXISTING);
				}

				producto.setNombreArchivoImagen(storageFileName);

			}
			producto.setNombre(productoDto.getNombre());
			producto.setMarca(productoDto.getMarca());
			producto.setCategoria(productoDto.getCategoria());
			producto.setPrecio(productoDto.getPrecio());
			producto.setDescripcion(productoDto.getDescripcion());

			repo.save(producto);


		}catch(Exception ex) {
			System.out.println("Exepción al grabar la edición: " + ex.getMessage());
		}

		return"redirect:/productos";
	}

	@GetMapping("/eliminar")
	public String eliminarProducto(@RequestParam int id) {
		try {
			Producto producto = repo.findById(id).get();
			// Eliminar la imagen del producto
			Path rutaImagen = Paths.get("public/images" + producto.getNombreArchivoImagen());
			
			try {
				Files.delete(rutaImagen);
				
			}catch(Exception ex) {
				System.out.println("Excepción al eliminar: " + ex.getMessage());
			}
			
			//Eliminar elproducto de la base de datos 
			repo.delete(producto);
			
			
		}catch(Exception ex) {
			System.out.println("Excepción al eliminar" + ex.getMessage());
		}
		
		
		return "redirect:/productos";
		
		
		
	}
}