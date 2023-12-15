package com.elielbatiston.webfluxmongodb.wishlist.usecases.getidsallwishlist;

import com.elielbatiston.webfluxmongodb.wishlist.adapters.repositories.WishlistRepository;
import com.elielbatiston.webfluxmongodb.wishlist.adapters.repositories.models.WishlistModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class GetIdsAllWishlistUseCase {

	private final WishlistRepository repository;

	public GetIdsAllWishlistUseCase(final WishlistRepository repository) {
		this.repository = repository;
	}

	public Mono<String> execute() {
		return repository.findAll()
			.map(WishlistModel::getId)
			.collectList()
			.map(id -> String.join(",", id))
			.subscribeOn(Schedulers.parallel());
	}
}
