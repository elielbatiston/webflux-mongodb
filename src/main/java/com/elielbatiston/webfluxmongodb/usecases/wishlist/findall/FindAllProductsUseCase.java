package com.elielbatiston.webfluxmongodb.usecases.wishlist.findall;

import com.elielbatiston.webfluxmongodb.domains.gateways.WishlistGateway;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class FindAllProductsUseCase {

	private final WishlistGateway gateway;

	public FindAllProductsUseCase(final WishlistGateway gateway) {
		this.gateway = gateway;
	}

	public Mono<OutputFindAllProductsDTO> execute(final InputFindAllProductsDTO input) {
		return gateway.getWishlist(input.idCustomer())
			.flatMap(it -> Mono.just(OutputFindAllProductsDTO.fromDomain(it)));
	}
}
