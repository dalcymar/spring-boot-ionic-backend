package com.dalcy.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dalcy.domain.Categoria;
import com.dalcy.repositories.CategoriaRepository;
import com.dalcy.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Optional<Categoria> buscar(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		if(obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id :" + id
					+ ", Tipo :" + Categoria.class.getName());
		}
		return obj;
	}
}
