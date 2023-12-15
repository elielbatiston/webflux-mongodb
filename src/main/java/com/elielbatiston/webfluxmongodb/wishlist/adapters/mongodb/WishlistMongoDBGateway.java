package com.elielbatiston.webfluxmongodb.wishlist.adapters.mongodb;

import com.elielbatiston.webfluxmongodb.wishlist.adapters.repositories.WishlistRepository;
import com.elielbatiston.webfluxmongodb.wishlist.adapters.repositories.models.WishlistModel;
import com.elielbatiston.webfluxmongodb.wishlist.domains.Wishlist;
import com.elielbatiston.webfluxmongodb.wishlist.domains.gateways.WishlistGateway;
import com.elielbatiston.webfluxmongodb.wishlist.usecases.add.AddProductUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Service
public class WishlistMongoDBGateway implements WishlistGateway {

	private static final ThreadFactory THREAD_FACTORY =
		new CustomizableThreadFactory("database-");

	private static final Scheduler DB_SCHEDULER = Schedulers
		.fromExecutor(Executors.newFixedThreadPool(8, THREAD_FACTORY));

	private static final Logger log = LoggerFactory.getLogger(WishlistMongoDBGateway.class);

	private final WishlistRepository repository;

	public WishlistMongoDBGateway(final WishlistRepository repository) {
		this.repository = repository;
	}

	@Override
	public Mono<Wishlist> save(final Wishlist wishlist) {
		return repository.save(WishlistModel.fromDomain(wishlist))
			.flatMap(it -> Mono.just(it.toDomain()));
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

	@Override
	public Mono<Wishlist> findAllByWishlistId(final String wishlistId) {
		return repository.findById(wishlistId)
			.flatMap(model -> Mono.just(model.toDomain()))
			.subscribeOn(DB_SCHEDULER)
			.doOnNext(it -> log.info("Wishlist received - {}", wishlistId));
	}
}
