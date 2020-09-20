package com.matchlivros.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public final class LivrosController {
	
	@RequestMapping("/cadastrarLivro")
	public String form() {
		return "livros/formLivro";
	}
	
}
