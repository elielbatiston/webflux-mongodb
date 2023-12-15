package com.elielbatiston.webfluxmongodb.wishlist.usecases.findall;

import com.elielbatiston.webfluxmongodb.wishlist.domains.gateways.WishlistGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class FindAllProductsUseCase {

	private static final Logger log = LoggerFactory.getLogger(FindAllProductsUseCase.class);

	private final WishlistGateway gateway;

	public FindAllProductsUseCase(final WishlistGateway gateway) {
		this.gateway = gateway;
	}

	public Mono<OutputFindAllProductsDTO> execute(final InputFindAllProductsDTO input) {
		log.info("Get a customer wishlist - Customer {}", input.idCustomer());
		return gateway.getWishlist(input.idCustomer())
			.flatMap(it -> Mono.just(OutputFindAllProductsDTO.fromDomain(it)))
			.doOnNext(next -> log.info("Get a customer wishlist founded - Customer {}", input.idCustomer()));
	}
}
