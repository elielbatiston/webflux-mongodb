package com.elielbatiston.webfluxmongodb.wishlist.usecases.delete;

import com.elielbatiston.webfluxmongodb.wishlist.domains.Product;
import com.elielbatiston.webfluxmongodb.wishlist.domains.Wishlist;
import com.elielbatiston.webfluxmongodb.wishlist.domains.exceptions.ObjectNotFoundException;
import com.elielbatiston.webfluxmongodb.wishlist.domains.gateways.WishlistGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeleteProductUseCase {

	private static final Logger log = LoggerFactory.getLogger(DeleteProductUseCase.class);

	private final WishlistGateway gateway;

	public DeleteProductUseCase(final WishlistGateway gateway) {
		this.gateway = gateway;
	}

	public void execute(final InputDeleteProductDTO input) {
		log.info("Wishlist to be delete - Customer {} and Product {}", input.idCustomer(), input.idProduct());

		gateway.getWishlist(input.idCustomer())
			.flatMap(wishlist -> {
				final Product product = wishlist.getProducts().stream()
					.filter(it -> it.getId().equals(input.idProduct()))
					.findFirst()
					.orElseThrow(() -> new ObjectNotFoundException(
						String.format("Objeto %s não encontrado", input.idProduct())
					));
				wishlist.removeProduct(product);
				return gateway.save(wishlist);
			})
			.doOnNext(next -> log.info("Wishlist deleted - Customer {} and Total Product {}", next.getCustomer().getId(), next.getProducts().size()))
			.filter(wishlist -> wishlist.getProducts().isEmpty())
			.flatMap(wishlist -> {
				gateway.delete(wishlist.getId());
				return Mono.empty();
			})
			.switchIfEmpty(Mono.defer(() -> {
				log.info("Wishlist deleted");
				return Mono.empty();
			}))
			.subscribe();
	}
}
