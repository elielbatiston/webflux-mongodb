package com.elielbatiston.webfluxmongodb.wishlist.adapters.controllers;

import com.elielbatiston.webfluxmongodb.wishlist.domains.Wishlist;
import com.elielbatiston.webfluxmongodb.wishlist.usecases.add.AddProductUseCase;
import com.elielbatiston.webfluxmongodb.wishlist.usecases.add.InputAddProductDTO;
import com.elielbatiston.webfluxmongodb.wishlist.usecases.add.OutputAddProductDTO;
import com.elielbatiston.webfluxmongodb.wishlist.usecases.delete.DeleteProductUseCase;
import com.elielbatiston.webfluxmongodb.wishlist.usecases.delete.InputDeleteProductDTO;
import com.elielbatiston.webfluxmongodb.wishlist.usecases.find.FindProductUseCase;
import com.elielbatiston.webfluxmongodb.wishlist.usecases.find.InputFindProductDTO;
import com.elielbatiston.webfluxmongodb.wishlist.usecases.find.OutputFindProductDTO;
import com.elielbatiston.webfluxmongodb.wishlist.usecases.findall.FindAllProductsUseCase;
import com.elielbatiston.webfluxmongodb.wishlist.usecases.findall.InputFindAllProductsDTO;
import com.elielbatiston.webfluxmongodb.wishlist.usecases.findall.OutputFindAllProductsDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(WishlistController.WISHLIST_RESOURCE)
public class WishlistController {

	public static final String WISHLIST_RESOURCE = "/wishlist";
	private static final String WISHLIST_DELETE_A_PRODUCT_ENDPOINT = "/{idCustomer}/product/{idProduct}";
	private static final String WISHLIST_GET_WISHLIST_ENDPOINT = "/{idCustomer}";
	private static final String WISHLIST_GET_A_PRODUCT_IN_WISHLIST_ENDPOINT = "/{idCustomer}/{idProduct}";
	private static final String WISHLIST_GET_ALL_WISHLIST = "/all";
	private static final String WISHLIST_GET_IDS_ALL_WISHLIST = "/ids";
	private static final Logger log = LoggerFactory.getLogger(WishlistController.class);
	public static final int CONCURRENCY = 2000;

	private final AddProductUseCase addProductUseCase;
	private final DeleteProductUseCase deleteProductUseCase;
	private final FindAllProductsUseCase findAllProductsUseCase;
	private final FindProductUseCase findProductUseCase;

	public WishlistController(
		final AddProductUseCase addProductUseCase,
		final DeleteProductUseCase deleteProductUseCase,
		final FindAllProductsUseCase findAllProductsUseCase,
		final FindProductUseCase findProductUseCase
	) {
		this.addProductUseCase = addProductUseCase;
		this.deleteProductUseCase = deleteProductUseCase;
		this.findAllProductsUseCase = findAllProductsUseCase;
		this.findProductUseCase = findProductUseCase;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<OutputAddProductDTO> save(@Valid @RequestBody final InputAddProductDTO dto) {
		log.info("Wishlist to be add - Customer {} and Product {}", dto.customer().id(), dto.product().id());
		return addProductUseCase.execute(dto)
			.doOnNext(next -> log.info("Wishlist added - Customer {} and Product {}", dto.customer().id(), dto.product().id()));
	}

	@DeleteMapping(WISHLIST_DELETE_A_PRODUCT_ENDPOINT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAProduct(
		@PathVariable final String idCustomer,
		@PathVariable final String idProduct
	) {
		final InputDeleteProductDTO input = new InputDeleteProductDTO(
			idCustomer,
			idProduct
		);
		deleteProductUseCase.execute(input);
	}

	@GetMapping(WISHLIST_GET_WISHLIST_ENDPOINT)
	@ResponseStatus(HttpStatus.OK)
	public Mono<OutputFindAllProductsDTO> getWishlist(@PathVariable final String idCustomer) {
		log.info("Get a customer wishlist - Customer {}", idCustomer);
		final InputFindAllProductsDTO input = new InputFindAllProductsDTO(idCustomer);
		return findAllProductsUseCase.execute(input)
			.doOnNext(next -> log.info("Get a customer wishlist founded - Customer {}", idCustomer);
	}

	@GetMapping(WISHLIST_GET_A_PRODUCT_IN_WISHLIST_ENDPOINT)
	@ResponseStatus(HttpStatus.OK)
	public Mono<OutputFindProductDTO> getAProduct(
		@PathVariable final String idCustomer,
		@PathVariable final String idProduct
	) {
		log.info("Get a product into customer wishlist - Customer {} and Product {}", idCustomer, idProduct);
		final InputFindProductDTO input = new InputFindProductDTO(idCustomer, idProduct);
		return findProductUseCase.execute(input)
			.doOnNext(next -> log.info("Get a product into customer wishlist founded - Customer {} and Product {}", idCustomer, idProduct)));
	}
}
