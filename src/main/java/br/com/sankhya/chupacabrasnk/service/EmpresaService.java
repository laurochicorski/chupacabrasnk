package br.com.sankhya.chupacabrasnk.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.sankhya.chupacabrasnk.entity.Empresa;
import br.com.sankhya.chupacabrasnk.repository.EmpresaRepository;

@Service
public class EmpresaService {
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	public Page<Empresa> findAll(Pageable pageable) {
		return empresaRepository.findAll(pageable);
	}
}
