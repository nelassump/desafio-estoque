package br.com.desafio.estoque.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(value = "Produto")
public class Products {

	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "Código do produto")
	private long id;

	@Min(value = 1, message = "{price.min}")
	@ApiModelProperty(notes = "Preço")
	private double price;

	@NotEmpty(message = "{suplier.error}")
	@Length(min = 5, max = 50, message = "{suplier.lenght}")
	@ApiModelProperty(notes = "Fornecedor")
	private String suplier;

	@NotEmpty(message = "{description.error}")
	@Length(min = 15, max = 500, message = "{description.length}")
	@ApiModelProperty(notes = "Descrição")
	private String description;

	@Min(value = 0, message = "{quantity.min}")
	@ApiModelProperty(notes = "Quantidade")
	private int quantity;

	public Products() {
		super();
	}

	public Products(
					@Min(value = 1, message = "{price.min}") double price,
					@Min(value = 1, message = "{quantity.min}") int quantity,
					@NotEmpty(message = "{suplier.error}") @Length(min = 5, max = 50, message = "{suplier.lenght}") String suplier,
					@NotEmpty(message = "{description.error}") @Length(min = 15, max = 500, message = "{description.length}") String description) {

		super();
		this.price = price;
		this.quantity = quantity;
		this.suplier = suplier;
		this.description = description;

	}

	public long getProductId() {
		return id;
	}

	public void setProductId(long productId) {
		this.id = productId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getSuplier() {
		return suplier;
	}

	public void setSuplier(String suplier) {
		this.suplier = suplier;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
