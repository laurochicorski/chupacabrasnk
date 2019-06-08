package br.com.sankhya.chupacabrasnk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.sankhya.chupacabrasnk.entity.Produto;
import br.com.sankhya.chupacabrasnk.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	public Page<Produto> findAll(Pageable pageable) {
		return produtoRepository.findAll(pageable);
	}
	
	public List<Produto> findAll() {
		return produtoRepository.findAll();
	}

}
