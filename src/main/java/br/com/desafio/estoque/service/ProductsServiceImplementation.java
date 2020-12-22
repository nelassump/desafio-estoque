package br.com.desafio.estoque.service;

import br.com.desafio.estoque.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.desafio.estoque.model.Products;
import javassist.NotFoundException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Service
public class ProductsServiceImplementation implements ProductsService{
	@Autowired
	private ProductRepository repository;
	
	@Override
	public Products createProduct(Products product) {
		Products products = repository.save(product);
		return products;
	}

	@Override
	public Object listAllProducts(Pageable page) {
		return repository.findAll(page);
	}
	
	@Override
	public Products searchProductById(Long id) throws NotFoundException{
		Products products = idExists(id);
		return products;
	}
	
	private Products idExists(Long id) throws NotFoundException  {
		Products products = repository.findById(id).orElse(null);
		if(products == null) {
			throw new NotFoundException("Não foi possível encontrar o produto com id: " + id);
		}
		return products;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = MethodArgumentNotValidException.class)
	public Products updateStock(Long id, Integer sum) throws NotFoundException {
		Products products = idExists(id);
		products.setQuantity((products.getQuantity() + sum));
		products = repository.save(products);
		return products;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = MethodArgumentNotValidException.class)
	public Products sellProduct(Long id, Integer subtraction) throws NotFoundException {
		Products products = idExists(id);
		products.setQuantity((products.getQuantity() - subtraction));
		products = repository.save(products);
		return products;
	}
}
