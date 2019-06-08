package br.com.sankhya.chupacabrasnk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.sankhya.chupacabrasnk.entity.Empresa;
import br.com.sankhya.chupacabrasnk.entity.Parceiro;

public interface ParceiroRepository extends JpaRepository<Parceiro, Long>, JpaSpecificationExecutor<Empresa>{

}
