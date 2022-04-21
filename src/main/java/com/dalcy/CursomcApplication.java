package com.dalcy;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.dalcy.domain.Categoria;
import com.dalcy.domain.Cidade;
import com.dalcy.domain.Cliente;
import com.dalcy.domain.Endereco;
import com.dalcy.domain.Estado;
import com.dalcy.domain.Pagamento;
import com.dalcy.domain.PagamentoComBoleto;
import com.dalcy.domain.PagamentoComCartao;
import com.dalcy.domain.Pedido;
import com.dalcy.domain.Produto;
import com.dalcy.domain.enums.EstadoPagamento;
import com.dalcy.domain.enums.TipoCliente;
import com.dalcy.repositories.CategoriaRepository;
import com.dalcy.repositories.CidadeRepository;
import com.dalcy.repositories.ClienteRepository;
import com.dalcy.repositories.EnderecoRepository;
import com.dalcy.repositories.EstadoRepository;
import com.dalcy.repositories.PagamentoRepository;
import com.dalcy.repositories.PedidoRepository;
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
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
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
		
		//Instanciando o Cliente usando as prioridades
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("27363323","93838393"));
		
		//Instanciando o Endereço usando a ordem de prioridades
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		
		//cliente conhecendo os endereços
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.save(cli1);
		
		enderecoRepository.save(e1);
		enderecoRepository.save(e2);
		
		//Relacionado ao instante do pedido
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		//Gerando os pedidos (instaciar)
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2021 11:13"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("20/11/2021 11:13"), cli1, e2);
		
		//Gerando os pagamento (instaciar)
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("22/10/2021 00:00"), null);
		ped2.setPagamento(pagto2);
		
		//Associando os clientes aos pedidos
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		//Save pedido
		pedidoRepository.save(ped1);
		pedidoRepository.save(ped2);
		
		// Save pagamento
		pagamentoRepository.save(pagto1);
		pagamentoRepository.save(pagto2);
	}	
}
