package br.com.mvc.kaio.controller;

import br.com.mvc.kaio.dto.ProductDto;
import br.com.mvc.kaio.repository.CategoryRepository;
import br.com.mvc.kaio.exception.DataNotFoundException;
import br.com.mvc.kaio.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("product")
public class ProductController {
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductRepository productRepository;

	@GetMapping("form")
	public String form(ProductDto productDto, Model model) {
		var categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);
		return "product/formulario";
	}
	
	@PostMapping("new")
	public String save(@Valid ProductDto productDto, BindingResult result) {
		if(result.hasErrors())
			return "product/formulario";

		var product = productDto.toProduct();
		var categoryOptional = categoryRepository.findById(productDto.getCategoryId());
		if(!categoryOptional.isPresent())
			throw new DataNotFoundException();
		product.setCategory(categoryOptional.get());

		if (product.getId() != null) {
			productRepository.save(product);
			return "redirect:/home";
		}

		product.setAvailable(true);
		productRepository.save(product);
		return "redirect:/home";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		productRepository.deleteById(id);
		return "redirect:/home";
	}

	@GetMapping("/update/{id}")
	public String showFormForUpdate(@PathVariable( value = "id") Long id, Model model) {
		var product = productRepository.findById(id);
		model.addAttribute("productDto", product.get().toProductDto(product.get()));

		var categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);

		return "product/formulario";
	}
	
}
