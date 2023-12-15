package com.elielbatiston.webfluxmongodb.wishlist.domains.gateways;

import com.elielbatiston.webfluxmongodb.wishlist.domains.Wishlist;
import reactor.core.publisher.Mono;

public interface WishlistGateway {

	Mono<Wishlist> save(final Wishlist wishlist);
	void delete(final String id);
	Mono<Wishlist> getWishlist(final String idCustomer);
	Mono<Wishlist> findAllByWishlistId(final String wishlistId);
}
