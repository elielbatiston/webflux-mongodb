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
import com.elielbatiston.webfluxmongodb.wishlist.usecases.findallwishlistbyids.FindAllWishlistByIdsUseCase;
import com.elielbatiston.webfluxmongodb.wishlist.usecases.findallwishlistbyids.InputFindAllWishlistByIds;
import com.elielbatiston.webfluxmongodb.wishlist.usecases.getidsallwishlist.GetIdsAllWishlistUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(WishlistController.WISHLIST_RESOURCE)
public class WishlistController {

	public static final String WISHLIST_RESOURCE = "/wishlist";
	private static final String WISHLIST_DELETE_A_PRODUCT_ENDPOINT = "/{idCustomer}/product/{idProduct}";
	private static final String WISHLIST_GET_WISHLIST_ENDPOINT = "/{idCustomer}";
	private static final String WISHLIST_GET_A_PRODUCT_IN_WISHLIST_ENDPOINT = "/{idCustomer}/{idProduct}";
	private static final String WISHLIST_GET_ALL_WISHLIST = "/all";
	private static final String WISHLIST_GET_IDS_ALL_WISHLIST = "/ids";

	private final AddProductUseCase addProductUseCase;
	private final DeleteProductUseCase deleteProductUseCase;
	private final FindAllProductsUseCase findAllProductsUseCase;
	private final FindProductUseCase findProductUseCase;
	private final FindAllWishlistByIdsUseCase findAllWishlistByIdsUseCase;
	private final GetIdsAllWishlistUseCase getIdsAllWishlistUseCase;

	public WishlistController(
		final AddProductUseCase addProductUseCase,
		final DeleteProductUseCase deleteProductUseCase,
		final FindAllProductsUseCase findAllProductsUseCase,
		final FindProductUseCase findProductUseCase,
		final FindAllWishlistByIdsUseCase findAllWishlistByIdsUseCase,
		final GetIdsAllWishlistUseCase getIdsAllWishlistUseCase
	) {
		this.addProductUseCase = addProductUseCase;
		this.deleteProductUseCase = deleteProductUseCase;
		this.findAllProductsUseCase = findAllProductsUseCase;
		this.findProductUseCase = findProductUseCase;
		this.findAllWishlistByIdsUseCase = findAllWishlistByIdsUseCase;
		this.getIdsAllWishlistUseCase = getIdsAllWishlistUseCase;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<OutputAddProductDTO> save(@Valid @RequestBody final InputAddProductDTO input) {
		return addProductUseCase.execute(input);
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
	public Mono<OutputFindAllProductsDTO> getWishlistByCustomer(@PathVariable final String idCustomer) {
		final InputFindAllProductsDTO input = new InputFindAllProductsDTO(idCustomer);
		return findAllProductsUseCase.execute(input);
	}

	@GetMapping(WISHLIST_GET_A_PRODUCT_IN_WISHLIST_ENDPOINT)
	@ResponseStatus(HttpStatus.OK)
	public Mono<OutputFindProductDTO> getAProduct(
		@PathVariable final String idCustomer,
		@PathVariable final String idProduct
	) {
		final InputFindProductDTO input = new InputFindProductDTO(idCustomer, idProduct);
		return findProductUseCase.execute(input);
	}

	@GetMapping(WISHLIST_GET_ALL_WISHLIST)
	public Flux<Wishlist> findAllById(@RequestParam final String ids) {
		final var input = new InputFindAllWishlistByIds(ids);
		return findAllWishlistByIdsUseCase.execute(input);
	}

	@GetMapping(WISHLIST_GET_IDS_ALL_WISHLIST)
	public Mono<String> getIds() {
		return getIdsAllWishlistUseCase.execute();
	}
}
