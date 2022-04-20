package com.dalcy;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.dalcy.domain.Categoria;
import com.dalcy.domain.Cidade;
import com.dalcy.domain.Estado;
import com.dalcy.domain.Produto;
import com.dalcy.repositories.CategoriaRepository;
import com.dalcy.repositories.CidadeRepository;
import com.dalcy.repositories.EstadoRepository;
import com.dalcy.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//Instanciado a categoria 
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório Tecnologico");
		
		//Instanciado o produto
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse",  80.00);
		
		
		// essa forma é mais rapida e gasta menos espaço
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		// Posso usar essa forma porém gastarei mas espaço.
		/*
		 	cat1.getProdutos().add(p1);
			cat1.getProdutos().add(p2);
			cat1.getProdutos().add(p3);
		
			cat2.getProdutos().add(p2);
		*/
			
		//produtos
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.save(cat1);
		categoriaRepository.save(cat2);
		
		// Salvar os produtos
		//produtoRepository.save(Arrays.asList(p1, p2, p3));
		produtoRepository.save(p1);
		produtoRepository.save(p2);
		produtoRepository.save(p3);
		
		//Instanciando a classe estado
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		//Instanciando a classe cidade
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "Sertãozinho", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		//Salvando o estado primeiro depois as cidades
		//estadoRepository.save(Arrays.asList(est1, est2));estar gerando erro
		estadoRepository.save(est1);
		estadoRepository.save(est2);
		
		//cidadeRepository.save(Arrays.asList(c1, c2, c3));
		cidadeRepository.save(c1);
		cidadeRepository.save(c2);
		cidadeRepository.save(c3);
	}	
}
