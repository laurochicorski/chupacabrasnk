package br.com.sankhya.chupacabrasnk.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import br.com.sankhya.chupacabrasnk.entity.Parceiro;

public class LeitorNFe {
	
	private static String PATH = "C:/xmls";
	
	public void processar() throws JDOMException, IOException {
		SAXBuilder sax = new SAXBuilder();
		
		File diretorio = new File(PATH);
		
		File[] arquivos = diretorio.listFiles();
		
		for (File file : arquivos) {
			InputStream input = new FileInputStream(file);
			Document doc = sax.build(input);
			
			Element nfeElem = doc.getRootElement();
			
			Element destElem = SimpleXPath.selectSingleNode(nfeElem, "infNFe/dest");
			
			if (destElem != null) {
				System.out.println(getParceiro(destElem).toString());
				
			}
			
		}
	}
	
	public Parceiro getParceiro(Element destElem) {
		Parceiro parceiro = new Parceiro();
		
		Element cnpjElem = SimpleXPath.selectSingleNode(destElem, "CNPJ");
		
		if (cnpjElem != null) {
			parceiro.setCnpj(cnpjElem.getText());
		}
		
		Element xNomeElem = SimpleXPath.selectSingleNode(destElem, "xNome");
		
		if (xNomeElem != null) {
			parceiro.setRazao(xNomeElem.getText());
		}
		
		Element ieElement = SimpleXPath.selectSingleNode(destElem, "IE");
		
		if (ieElement != null) {
			parceiro.setIe(ieElement.getText());
		}
		
		
		
		return parceiro;
	}

}
