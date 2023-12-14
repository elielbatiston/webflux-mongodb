package com.elielbatiston.webfluxmongodb.wishlist.usecases.find;

import com.elielbatiston.webfluxmongodb.wishlist.domains.Wishlist;
import com.elielbatiston.webfluxmongodb.wishlist.domains.exceptions.ObjectNotFoundException;
import com.elielbatiston.webfluxmongodb.wishlist.domains.gateways.WishlistGateway;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class FindProductUseCase {

	private final WishlistGateway gateway;

	public FindProductUseCase(WishlistGateway gateway) {
		this.gateway = gateway;
	}

	public Mono<OutputFindProductDTO> execute(final InputFindProductDTO input) {
		final Mono<Wishlist> wishlistMono = gateway.getWishlist(input.idCustomer());
		return wishlistMono
			.flatMap(wishlist -> {
				final var outputFindAProductCustomerDTO = OutputFindProductDTO.OutputFindAProductCustomerDTO
					.fromDomain(Objects.requireNonNull(wishlist.getCustomer()));
				final var product = wishlist.getProducts().stream()
					.filter(product1 -> product1.getId().equals(input.idProduct()))
					.findFirst()
					.orElseThrow(() -> new ObjectNotFoundException(
						String.format("Objeto %s não encontrado", input.idProduct())
					));
				final var outputFindAProductProductDTO = OutputFindProductDTO.OutputFindAProductProductDTO
					.fromDomain(Objects.requireNonNull(product));
				return Mono.just(new OutputFindProductDTO(
					outputFindAProductCustomerDTO,
					outputFindAProductProductDTO
				));
			});
	}
}
