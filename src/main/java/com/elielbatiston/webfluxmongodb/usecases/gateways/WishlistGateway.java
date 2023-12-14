package com.elielbatiston.webfluxmongodb.usecases.gateways;

import com.elielbatiston.webfluxmongodb.domains.Wishlist;
import reactor.core.publisher.Mono;

public interface WishlistGateway {

	void save(final Wishlist wishlist);
	void delete(final String id);
	Mono<Wishlist> getWishlist(final String idCustomer);
}
