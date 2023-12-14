package com.elielbatiston.webfluxmongodb.usecases.wishlist.add;

import com.elielbatiston.webfluxmongodb.configs.WishlistConfig;
import com.elielbatiston.webfluxmongodb.domains.Customer;
import com.elielbatiston.webfluxmongodb.domains.Wishlist;
import com.elielbatiston.webfluxmongodb.domains.exceptions.DataIntegrityException;
import com.elielbatiston.webfluxmongodb.domains.exceptions.ObjectNotFoundException;
import com.elielbatiston.webfluxmongodb.usecases.gateways.WishlistGateway;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AddProductUseCase {

	private final WishlistConfig config;

	private final WishlistGateway gateway;

	public AddProductUseCase(final WishlistConfig config, final WishlistGateway gateway) {
		this.config = config;
		this.gateway = gateway;
	}

	public Mono<Wishlist> execute(final InputAddProductDTO input) {
		final Integer maximumLimitAllowed = config.getWishlistProductsProperties().getMaximumLimitAllowed();
		final Mono<Wishlist> wishlist = this.getWishlistOrNew(input);
		wishlist.flatMap(it -> {
			it.addProduct(input.product().toDomain());

			if (it.getProducts().size() > maximumLimitAllowed) {
				return Mono.error(
					new DataIntegrityException(String.format("Quantidade máxima de produtos excedida (%s)", maximumLimitAllowed))
				);
			}

			return Mono.just(it);
		});

		return gateway.save(wishlist);
	}

	private Mono<Wishlist> getWishlistOrNew(final InputAddProductDTO input) {
		try {
			final Mono<Wishlist> wishlist = gateway.getWishlist(input.customer().id());
			wishlist
				.flatMap(it -> {
					it.getCustomer().changeName(input.customer().name());
					return Mono.just(it);
				});

			return wishlist;
		} catch (final ObjectNotFoundException ex) {
			final Customer customer = new Customer(
				input.customer().id(),
				input.customer().name()
			);
			return Mono.just(new Wishlist(customer));
		}
	}
}
