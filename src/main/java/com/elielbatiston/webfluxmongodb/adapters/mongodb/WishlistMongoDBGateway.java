package com.elielbatiston.webfluxmongodb.adapters.mongodb;

import com.elielbatiston.webfluxmongodb.adapters.repositories.WishlistRepository;
import com.elielbatiston.webfluxmongodb.adapters.repositories.models.WishlistModel;
import com.elielbatiston.webfluxmongodb.domains.Wishlist;
import com.elielbatiston.webfluxmongodb.usecases.gateways.WishlistGateway;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class WishlistMongoDBGateway implements WishlistGateway {

	private final WishlistRepository repository;

	public WishlistMongoDBGateway(final WishlistRepository repository) {
		this.repository = repository;
	}

	@Override
	public Mono<Wishlist> save(final Wishlist wishlist) {
		return repository.save(WishlistModel.fromDomain(wishlist))
			.flatMap(model -> Mono.just(model.toDomain()));
	}

	@Override
	public void delete(final String id) {
		repository.deleteById(id);
	}

	@Override
	public Mono<Wishlist> getWishlist(final String idCustomer) {
		return repository.findByIdCustomer(idCustomer)
			.flatMap(model -> Mono.just(model.toDomain()));
	}
}
