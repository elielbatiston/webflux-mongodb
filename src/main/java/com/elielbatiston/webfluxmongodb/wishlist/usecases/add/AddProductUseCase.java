package com.elielbatiston.webfluxmongodb.wishlist.usecases.add;

import com.elielbatiston.webfluxmongodb.configs.WishlistConfig;
import com.elielbatiston.webfluxmongodb.wishlist.adapters.controllers.WishlistController;
import com.elielbatiston.webfluxmongodb.wishlist.domains.Customer;
import com.elielbatiston.webfluxmongodb.wishlist.domains.Wishlist;
import com.elielbatiston.webfluxmongodb.wishlist.domains.exceptions.DataIntegrityException;
import com.elielbatiston.webfluxmongodb.wishlist.domains.gateways.WishlistGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Service
public class AddProductUseCase {

	private static final Logger log = LoggerFactory.getLogger(AddProductUseCase.class);

	private final WishlistConfig config;

	private final WishlistGateway gateway;

	public AddProductUseCase(final WishlistConfig config, final WishlistGateway gateway) {
		this.config = config;
		this.gateway = gateway;
	}

	public Mono<OutputAddProductDTO> execute(final InputAddProductDTO input) {
		log.info("Wishlist to be add - Customer {} and Product {}", input.customer().id(), input.product().id());

		final Integer maximumLimitAllowed = config.getWishlistProductsProperties().getMaximumLimitAllowed();
		final Mono<Wishlist> wishlistMono = this.getWishlistOrNew(input);
		return wishlistMono
			.flatMap(wishlist -> {
				wishlist.addProduct(input.product().toDomain());

				if (wishlist.getProducts().size() > maximumLimitAllowed) {
					return Mono.error(
						new DataIntegrityException(String.format("Quantidade máxima de produtos excedida (%s)", maximumLimitAllowed))
					);
				}

				return Mono.just(wishlist);
			})
			.doOnNext(next -> log.info("Wishlist received - Customer {}", next.getCustomer().getId()))
			.flatMap(gateway::save)
			.doOnNext(next -> log.info("Saving wishlist - Customer {} and Product {}", input.customer().id(), input.product().id()))
			.flatMap(transform -> {
				final var customerDTO = new OutputAddProductDTO.CustomerDTO(
					transform.getCustomer().getId(),
					transform.getCustomer().getName()
				);
				final var products = transform.getProducts().stream()
					.map(product -> new OutputAddProductDTO.ProductDTO(
						product.getId(),
						product.getName(),
						product.getPrice()
					))
					.collect(Collectors.toSet());

				return Mono.just(new OutputAddProductDTO(
					transform.getId(),
					customerDTO,
					products
				));
			})
			.doOnNext(next -> log.info("Wishlist added - Customer {} and Product {}", input.customer().id(), input.product().id()));
	}

	private Mono<Wishlist> getWishlistOrNew(final InputAddProductDTO input) {
		return gateway.getWishlist(input.customer().id())
				.flatMap(wishlist -> {
					wishlist.getCustomer().changeName(input.customer().name());
					return Mono.just(wishlist);
				})
			.switchIfEmpty(Mono.defer(() -> {
				final var customer = new Customer(
					input.customer().id(),
					input.customer().name()
				);
				final var wishlist = new Wishlist(customer);
				return Mono.just(wishlist);
			}));
	}
}
