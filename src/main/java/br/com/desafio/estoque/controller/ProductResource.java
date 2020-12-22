package br.com.desafio.estoque.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.desafio.estoque.model.Products;
import br.com.desafio.estoque.service.ProductsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.NotFoundException;

@RestController
@Api(description = "Gerenciamento de produtos")
@RequestMapping(value = "/operation", produces = {APPLICATION_JSON_VALUE, APPLICATION_JSON_VALUE})
public class ProductResource {
		
	@Autowired
	ProductsService service;

	@PostMapping("/register")
	@ResponseStatus(value = HttpStatus.CREATED)
	@ApiOperation(value = "Cadastrar produto")
	@ApiResponses(value = {@ApiResponse(code = 201, message = "Produto cadastrado com sucesso.")})
	@ApiImplicitParams({@ApiImplicitParam(name = "product", value = "Estrutura para cadastro de produto.", dataType = "CadastroProdutoDTO", required = true)
	})
	public ResponseEntity<Products> registerProduct(@Valid @RequestBody Products product) {
		Products products = service.createProduct(product);
		return new ResponseEntity<>(products, HttpStatus.CREATED);
	}

	@GetMapping("/query/by/{id}")
	@ApiOperation(value = "Consulta produto por ID")
	@ApiResponses(value = {@ApiResponse(code = 404, message = "Produto não Encontrado.")})
	@ApiImplicitParams({@ApiImplicitParam(name = "id", value = "Código do produto:", dataType = "Long", required = true)
	})
	public ResponseEntity<Products> getProductById(@PathVariable Long id) throws NotFoundException {
		Products products = service.searchProductById(id);
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@GetMapping("/query/all")
	@ApiOperation(value = "Consultar todos os produtos cadastrados")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "page", defaultValue = "0",  dataType = "int",   value = "Página:"),
		@ApiImplicitParam(name = "limit",defaultValue = "3",  dataType = "int",   value = "Limite de cadastros:"),
		@ApiImplicitParam(name = "field",defaultValue = "id", dataType = "String",value = "Ordenar por id/price/suplier:"),
		@ApiImplicitParam(name = "sort", defaultValue = "asc",dataType = "String",value = "Ordenar por asc/desc:")
	})
	public ResponseEntity<Object> getAllProducts(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit",defaultValue = "3") int limit,
			@RequestParam(value = "field", defaultValue = "id") String field,
			@RequestParam(value = "sort", defaultValue = "asc") String order){
		return ResponseEntity.ok(service.listAllProducts(PageRequest.of(page, limit, Direction.fromString(order), field)));
	}

    @PutMapping("/stock/update/{id}")
    @ApiResponses(value = {
			@ApiResponse(code = 200, message = "Estoque atualizado com sucesso."),
    		@ApiResponse(code = 404, message = "Produto Não Encontrado.")})
    @ApiOperation(value = "Altera o estoque do produto por ID", notes = "Adiciona ao estoque atual a quantidade informada.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Código do produto:", dataType = "Long", required = true),
            @ApiImplicitParam(name = "sum", value = "Quantidade a ser adicionada ao estoque:", dataType = "int", required = true)
    })
    public ResponseEntity<Products> updateStock(@PathVariable Long id, @RequestParam Integer sum) throws NotFoundException {
        Products products = service.updateStock(id, sum);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

	@PutMapping("sell/{id}")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Produto vendido com sucesso."),
			@ApiResponse(code = 404, message = "Produto não encontrado.")})
	@ApiOperation(value = "Executa operação de venda por ID", notes = "Subtrai do estoque atual a quantidade informada.")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "Código do produto:", dataType = "Long", required = true),
			@ApiImplicitParam(name = "subtraction", value = "Quantidade a ser subtraída do estoque:", dataType = "int", required = true)
	})
	public ResponseEntity<Void> sellProduct(@PathVariable Long id, Integer subtraction) throws NotFoundException  {
		service.sellProduct(id, subtraction);
		return ResponseEntity.noContent().build();
	}

}
