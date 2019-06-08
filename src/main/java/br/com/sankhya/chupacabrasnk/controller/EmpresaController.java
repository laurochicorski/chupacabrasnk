package br.com.sankhya.chupacabrasnk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.sankhya.chupacabrasnk.entity.Empresa;
import br.com.sankhya.chupacabrasnk.service.EmpresaService;

@CrossOrigin
@RestController
public class EmpresaController {

	@Autowired
	private EmpresaService empresaService;
	
	@GetMapping("/empresa")
	public Page<Empresa> buscarTodos(@RequestParam(value = "pagina", required = true)int pagina,
						            @RequestParam(value = "qtd", required = true)int qtd) throws Exception {
		return empresaService.findAll(new PageRequest(pagina, qtd));
	}
}
