package com.elielbatiston.webfluxmongodb.wishlist.usecases.add;

import com.elielbatiston.webfluxmongodb.configs.WishlistConfig;
import com.elielbatiston.webfluxmongodb.wishlist.domains.Customer;
import com.elielbatiston.webfluxmongodb.wishlist.domains.Wishlist;
import com.elielbatiston.webfluxmongodb.wishlist.domains.exceptions.DataIntegrityException;
import com.elielbatiston.webfluxmongodb.wishlist.domains.exceptions.ObjectNotFoundException;
import com.elielbatiston.webfluxmongodb.wishlist.domains.gateways.WishlistGateway;
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
			.flatMap(gateway::save);
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
