package br.com.sankhya.chupacabrasnk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.sankhya.chupacabrasnk.entity.Parceiro;
import br.com.sankhya.chupacabrasnk.service.ParceiroService;

@CrossOrigin
@RestController
public class ParceiroController {
	
	@Autowired
	private ParceiroService parceiroService;
	
	@GetMapping("/parceiro")
	public Page<Parceiro> buscarTodos(@RequestParam(value = "pagina", required = true)int pagina,
            @RequestParam(value = "qtd", required = true)int qtd) throws Exception {
		return parceiroService.findAll(new PageRequest(pagina, qtd));	
	}
	
	@GetMapping("/parceiros")
	public List<Parceiro> buscarTodos() throws Exception {
		return parceiroService.findAll();	
	}

}
