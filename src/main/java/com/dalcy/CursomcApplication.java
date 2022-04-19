package com.dalcy;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.dalcy.domain.Categoria;
import com.dalcy.domain.Produto;
import com.dalcy.repositories.CategoriaRepository;
import com.dalcy.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
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
	}	
}
