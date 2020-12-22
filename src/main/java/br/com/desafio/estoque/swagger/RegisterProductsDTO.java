package br.com.desafio.estoque.swagger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "CadastroProdutoDTO")
public class RegisterProductsDTO {

	@ApiModelProperty(notes = "Preço")
	private double price;
	@ApiModelProperty(notes = "Quantidade")
	private int quantity;
	@ApiModelProperty(notes = "Fornecedor")
	private String suplier;
	@ApiModelProperty(notes = "Descrição")
	private String description;

	public double getPrice() { return price; }

	public void setPrice(double price) { this.price = price; }

	public int getQuantity() { return quantity;	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getSuplier() { return suplier; }

	public void setSuplier(String suplier) { this.suplier = suplier; }

	public String getDescription() { return description;	}

	public void setDescription(String description) { this.description = description; }
}
