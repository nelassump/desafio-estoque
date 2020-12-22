package br.com.desafio.estoque.service;

import org.springframework.data.domain.Pageable;

import br.com.desafio.estoque.model.Products;
import javassist.NotFoundException;

public interface ProductsService {
	
	Products createProduct(Products product);
	
	Object listAllProducts(Pageable page);
	
	Products searchProductById(Long id) throws NotFoundException;

	Products updateStock(Long id, Integer sum) throws NotFoundException;
}
