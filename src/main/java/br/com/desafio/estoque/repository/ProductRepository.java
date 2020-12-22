package br.com.desafio.estoque.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.desafio.estoque.model.Products;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Products, Long> {

}
