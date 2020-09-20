package com.matchlivros.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.matchlivros.models.Livros;
import com.matchlivros.repository.LivrosRepository;

@Controller
public final class LivrosController {
	
	@Autowired
	private LivrosRepository lr;
	
	@RequestMapping(value="/cadastrarLivro", method=RequestMethod.GET)
	public String form() {
		return "livros/formLivro";
	}
	
	@RequestMapping(value="/cadastrarLivro", method=RequestMethod.POST)
	public String form(Livros livros) {
		lr.save(livros);
		
		return "redirect:/cadastrarLivro";
	}
	
}
