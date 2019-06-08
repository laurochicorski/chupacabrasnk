package br.com.sankhya.chupacabrasnk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.sankhya.chupacabrasnk.entity.Parceiro;
import br.com.sankhya.chupacabrasnk.service.ParceiroService;
import br.com.sankhya.chupacabrasnk.service.ProcessadorXmlService;

@CrossOrigin
@RestController
public class ProcessadorXmlController {
	
	@Autowired
	private ProcessadorXmlService processadorXmlService;
	
	@GetMapping("/processar")
	public void processar() throws Exception {
		processadorXmlService.processar();
	}

}
