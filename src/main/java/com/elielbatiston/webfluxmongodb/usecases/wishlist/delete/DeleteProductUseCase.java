package com.elielbatiston.webfluxmongodb.usecases.wishlist.delete;

import com.elielbatiston.webfluxmongodb.domains.Product;
import com.elielbatiston.webfluxmongodb.domains.Wishlist;
import com.elielbatiston.webfluxmongodb.domains.exceptions.ObjectNotFoundException;
import com.elielbatiston.webfluxmongodb.domains.gateways.WishlistGateway;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeleteProductUseCase {

	private final WishlistGateway gateway;

	public DeleteProductUseCase(final WishlistGateway gateway) {
		this.gateway = gateway;
	}

	public void execute(final InputDeleteProductDTO input) {
		final Mono<Wishlist> wishlistMono = gateway.getWishlist(input.idCustomer());
		wishlistMono
			.flatMap(wishlist -> {
				final Product product = wishlist.getProducts().stream()
					.filter(it -> it.getId().equals(input.idProduct()))
					.findFirst()
					.orElseThrow(() -> new ObjectNotFoundException(
						String.format("Objeto %s não encontrado", input.idProduct())
					));
				wishlist.removeProduct(product);
				gateway.save(Mono.just(wishlist));

				if (wishlist.getProducts().isEmpty()) {
					gateway.delete(wishlist.getId());
				}
				return Mono.empty();
			});
	}
}
