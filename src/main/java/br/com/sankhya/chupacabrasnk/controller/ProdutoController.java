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
import br.com.sankhya.chupacabrasnk.entity.Produto;
import br.com.sankhya.chupacabrasnk.service.ParceiroService;
import br.com.sankhya.chupacabrasnk.service.ProdutoService;

@CrossOrigin
@RestController
public class ProdutoController {
	
	@Autowired
	private ProdutoService produtoService;
	
	@GetMapping("/produto")
	public Page<Produto> buscarTodos(@RequestParam(value = "pagina", required = true)int pagina,
            @RequestParam(value = "qtd", required = true)int qtd) throws Exception {
		return produtoService.findAll(new PageRequest(pagina, qtd));	
	}
	
	@GetMapping("/produtos")
	public List<Produto> buscarTodos() throws Exception {
		return produtoService.findAll();	
	}

}
