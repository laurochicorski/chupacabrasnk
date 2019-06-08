package br.com.sankhya.chupacabrasnk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.sankhya.chupacabrasnk.entity.Parceiro;
import br.com.sankhya.chupacabrasnk.repository.ParceiroRepository;

@Service
public class ParceiroService {
	
	@Autowired
	private ParceiroRepository parceiroRepository;
	
	public Page<Parceiro> findAll(Pageable pageable) {
		return parceiroRepository.findAll(pageable);
	}

	public List<Parceiro> findAll() {
		return parceiroRepository.findAll();
	} 

}
