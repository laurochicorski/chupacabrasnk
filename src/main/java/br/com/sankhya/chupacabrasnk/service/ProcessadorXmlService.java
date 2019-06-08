package br.com.sankhya.chupacabrasnk.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sankhya.chupacabrasnk.entity.Parceiro;
import br.com.sankhya.chupacabrasnk.entity.Produto;
import br.com.sankhya.chupacabrasnk.repository.ParceiroRepository;
import br.com.sankhya.chupacabrasnk.repository.ProdutoRepository;
import br.com.sankhya.chupacabrasnk.utils.SimpleXPath;

@Service
public class ProcessadorXmlService {
	
	private static String PATH = "C:/xmls";
	
	@Autowired
	private ParceiroRepository parceiroRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	public void processar() throws JDOMException, IOException {
		SAXBuilder sax = new SAXBuilder();
		
		File diretorio = new File(PATH);
		
		File[] arquivos = diretorio.listFiles();
		
		for (File file : arquivos) {
			InputStream input = new FileInputStream(file);
			
			System.out.println("Iniciando importação do arquivo: " + file.getName());
			Document doc = sax.build(input);
			
			Element nfeElem = doc.getRootElement();
			
			Element destElem = SimpleXPath.selectSingleNode(nfeElem, ".//infNFe/dest");
			
			Parceiro parceiro = getParceiro(destElem);
			
			parceiroRepository.save(parceiro);
			
			Collection<Element> detsElem = SimpleXPath.selectNodes(nfeElem, ".//infNFe/det");
			
			List<Produto> produtos = getProdutos(detsElem);
			
			System.out.println(produtos.toString());
			produtoRepository.saveAll(produtos);
		}
	
	}
	
	private List<Produto> getProdutos(Collection<Element> detsElem) {
		ArrayList<Produto> produtos = new ArrayList<>();
		
		for (Element detElem : detsElem) {
			Produto produto = new Produto();
			
			Element prodElem = SimpleXPath.selectSingleNode(detElem, "prod");
			
			Element cProdElem = SimpleXPath.selectSingleNode(prodElem, "cProd");
			
			if (cProdElem != null) {
				produto.setCodProd(cProdElem.getText());
			}
			
			Element cEANElem = SimpleXPath.selectSingleNode(prodElem, "cEAN");
			
			if (cEANElem != null) {
				produto.setEan(cEANElem.getText());
			}
			
			Element xProdElem = SimpleXPath.selectSingleNode(prodElem, "xProd");
			
			if (xProdElem != null) {
				produto.setDescricao(xProdElem.getText());
			}
			
			Element NCMElem = SimpleXPath.selectSingleNode(prodElem, "NCM");
			
			if (NCMElem != null) {
				produto.setNcm(NCMElem.getText());
			}
			
			Element CESTElem = SimpleXPath.selectSingleNode(prodElem, "CEST");
			
			if (CESTElem != null) {
				produto.setCest(CESTElem.getText());
			}
			
			produtos.add(produto);
		}
		return produtos;
	}

	public Parceiro getParceiro(Element destElem) {
		Parceiro parceiro = new Parceiro();
		
		Element cnpjElem = SimpleXPath.selectSingleNode(destElem, "CNPJ");
		
		if (cnpjElem != null) {
			parceiro.setCnpj(cnpjElem.getText());
		}
		
		Element cpfElem = SimpleXPath.selectSingleNode(destElem, "CPF");
		
		if (cpfElem != null) {
			parceiro.setCnpj(cpfElem.getText());
		}
		
		Element xNomeElem = SimpleXPath.selectSingleNode(destElem, "xNome");
		
		if (xNomeElem != null) {
			parceiro.setRazao(xNomeElem.getText());
		}
		
		Element ieElement = SimpleXPath.selectSingleNode(destElem, "IE");
		
		if (ieElement != null) {
			parceiro.setIe(ieElement.getText());
		}
		
		Element enderDestElem = SimpleXPath.selectSingleNode(destElem, "enderDest");
		
		if (enderDestElem != null) {
			Element xLgrElem = SimpleXPath.selectSingleNode(enderDestElem, "xLgr");
			
			if (xLgrElem != null) {
				parceiro.setLogradouro(xLgrElem.getText());
			}
			
			Element nroElem = SimpleXPath.selectSingleNode(enderDestElem, "nro");
			
			if (nroElem != null) {
				parceiro.setNumero(nroElem.getText());
			}
			
			Element xCplElem = SimpleXPath.selectSingleNode(enderDestElem, "xCpl");
			
			if (xCplElem != null) {
				parceiro.setComplemento(xCplElem.getText());
			}
			
			Element xBairroElem = SimpleXPath.selectSingleNode(enderDestElem, "xBairro");
			
			if (xBairroElem != null) {
				parceiro.setBairro(xBairroElem.getText());
			}
		}
		
		System.out.println(parceiro.toString());
		
		return parceiro;
	}

}
