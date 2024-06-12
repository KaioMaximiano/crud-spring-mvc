package br.com.mvc.kaio.controller;

import br.com.mvc.kaio.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class HomeController {
	
	@Autowired
	private ProductRepository productRepository;

	@GetMapping("/home")
	public String viewHomePage(Model model) {
		return homePaginated(model,1, "name", "asc");
	}

	@GetMapping("/page/{pageNo}")
	public String homePaginated(Model model,
					   @PathVariable(value = "pageNo") int pageNo,
					   @RequestParam("sortField") String sortField,
					   @RequestParam("sortDir") String sortDir) {

		var sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
		PageRequest pageRequest = PageRequest.of(pageNo - 1,3, sort);
		var products = productRepository.findAll(pageRequest);
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", products.getTotalPages());
		model.addAttribute("totalItems", products.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("products", products);
		return "home";
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public String onError() {
		return "redirect:/home";
	}
}
