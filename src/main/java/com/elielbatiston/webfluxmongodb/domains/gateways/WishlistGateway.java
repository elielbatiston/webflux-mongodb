package com.elielbatiston.webfluxmongodb.domains.gateways;

import com.elielbatiston.webfluxmongodb.domains.Wishlist;
import reactor.core.publisher.Mono;

public interface WishlistGateway {

	Mono<Wishlist> save(final Wishlist wishlist);
	void delete(final String id);
	Mono<Wishlist> getWishlist(final String idCustomer);
}
