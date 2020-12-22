package br.com.desafio.estoque;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import java.util.ArrayList;
import java.util.List;

import br.com.desafio.estoque.model.Products;
import br.com.desafio.estoque.repository.ProductRepository;

import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductsEndpointTest {
	
	@Autowired
	private TestRestTemplate rest;

	@MockBean
	private ProductRepository repository;

	private Products product = new Products(999.99, 10, "Fornecedor teste A", "Descrição produto teste A.");


	@Test
	public void getProductById() {
		repository.save(product);
		ResponseEntity<Products> response = rest.getForEntity("/operation/query/all", Products.class, repository.findById(product.getProductId()));
		System.out.println("getProductById: " + response);
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
	}
	
	@Test
	public void getProductWithInvalidId() {
		long id = 99L;
		ResponseEntity<String> response = rest.getForEntity("/operation/query/by/{id}", String.class, id);
		System.out.println("getProductWithInvalidId: " + response);
		assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
	}

	@Test
	public void postProduct() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Products> request = new HttpEntity<>(product, headers);
		ResponseEntity<String> response = rest.postForEntity("/operation/register", request , String.class );
		System.out.println("postProduct: " + response);
		assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
	}
	
	@Test
	public void postInvalidProduct() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Products priceError = new Products(999, -1, "Fornecedor teste D", "Descrição produto teste D.");
		HttpEntity<Products> request = new HttpEntity<>(priceError, headers);
		ResponseEntity<String> response = rest.postForEntity("/operation/register", request , String.class );
		System.out.println("postInvalidProduct: " + response);
		assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
	}

	@Test
	public void getAllProducts() {
		List<Products> products = new ArrayList<>();
		products.add(new Products (1849.99, 30, "Fornecedor teste B", "Descrição produto teste B."));
		products.add(new Products (539.45, 5, "Fornecedor teste C", "Descrição produto teste C."));

		BDDMockito.when(repository.findAll()).thenReturn(products);
		ResponseEntity<String> response = rest.getForEntity("/operation/query/all", String.class);
		System.out.println("getAllProducts: " + response);
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
	}

	@Test
	public void updateStockWithInvalidId() {
		long id = 99L;
		ResponseEntity<String> response = rest.exchange("/stock/update/{id}", HttpMethod.PUT, null , String.class, id);
		System.out.println("updateStockWithInvalidId: " + response);
		assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
	}

	@Test
	public void sellProductWithInvalidId() {
		long id = 99L;
		ResponseEntity<String> response = rest.exchange("/sell/{id}", HttpMethod.PUT, null , String.class, id);
		System.out.println("sellProductWithInvalidId: " + response);
		assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
	}
}