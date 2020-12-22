package br.com.desafio.estoque;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.desafio.estoque.model.Products;
import br.com.desafio.estoque.repository.ProductRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductsRepositoryTest {
	@Autowired
	private  ProductRepository repository;
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	

	private double price = 535.00;
	private int quantity = 1;
	private String suplier = "Fornecedor teste";
	private String description = "Descrição do Produto Teste";

	private Products product = new Products(price, quantity, suplier, description);
	
	@Test
	public void createNewProduct() {
		this.repository.save(product);
		assertThat(product.getProductId()).isNotNull();
		assertThat(product.getPrice()).isEqualTo(535.00);
		assertThat(product.getQuantity()).isEqualTo(1);
		assertThat(product.getSuplier()).isEqualTo("Fornecedor teste");
		assertThat(product.getDescription()).isEqualTo("Descrição do Produto Teste");
	}
	
	@Test
	public void updateProductPrice() {
		product.setQuantity(99);
		this.repository.save(product);
		assertThat(product.getPrice()).isEqualTo(535.00);
		assertThat(product.getQuantity()).isEqualTo(99);
		assertThat(product.getSuplier()).isEqualTo("Fornecedor teste");
		assertThat(product.getDescription()).isEqualTo("Descrição do Produto Teste");
	}
	
	@Test
	public void createProductWithNegativeQuantityTest() {
		Products negativeQuantity = new Products(price, -1, suplier, description);
		thrown.expect(ConstraintViolationException.class);
		thrown.expectMessage("Verifique se a quantidade é maior ou igual a zero.");
		this.repository.save(negativeQuantity);
	}

	@Test
	public void createProductWithZeroPriceTest() {
		Products zeroPrice = new Products(0, quantity, suplier, description);
		thrown.expect(ConstraintViolationException.class);
		thrown.expectMessage("Verifique se o preço é maior que zero.");
		this.repository.save(zeroPrice);
	}

	@Test
	public void createProductWithoutInformationTest() {
		Products noInformation = new Products(0, 0, null, null);
		thrown.expect(ConstraintViolationException.class);
		this.repository.save(noInformation);
	}
	
	@Test
	public void createProductWithSmallDescriptionTest() {
		Products smallDescription = new Products(price, quantity, suplier, "Lorem Ipsum");
		thrown.expect(ConstraintViolationException.class);
		thrown.expectMessage("A descrição deve ter entre 15 a 500 caracteres.");
		this.repository.save(smallDescription);
	}
	
	@Test
	public void createProductWithoutDescriptionTest() {
		Products noDescription = new Products(price, quantity, suplier, null);
		thrown.expect(ConstraintViolationException.class);
		thrown.expectMessage("Descrição não pode estar em branco.");
		this.repository.save(noDescription);
	}

	@Test
	public void createProductWithSmallSuplierTest() {
		Products smallSuplier = new Products(price, quantity, "Lore", description);
		thrown.expect(ConstraintViolationException.class);
		thrown.expectMessage("O fornecedor deve ter entre 5 e 50 caracteres");
		this.repository.save(smallSuplier);
	}

	@Test
	public void createProductWithoutSuplierTest() {
		Products emptySuplier = new Products(price, quantity, null, description);
		thrown.expect(ConstraintViolationException.class);
		thrown.expectMessage("Fornecedor não pode estar em branco");
		this.repository.save(emptySuplier);
	}

	@Test
	public void updateStockTest() {
		product.setQuantity((product.getQuantity() + 1));
		this.repository.save(product);
		assertThat(repository.findById(product.getProductId())).isPresent();
		assertThat(product.getQuantity()).isEqualTo(2);
	}

	@Test
	public void sellProductTest() {
		product.setQuantity((product.getQuantity() - 1));
		this.repository.save(product);
		assertThat(repository.findById(product.getProductId())).isPresent();
		assertThat(product.getQuantity()).isEqualTo(0);
	}
}
