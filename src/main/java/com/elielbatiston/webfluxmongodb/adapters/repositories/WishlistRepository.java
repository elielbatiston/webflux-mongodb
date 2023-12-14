package com.elielbatiston.webfluxmongodb.adapters.repositories;

import com.elielbatiston.webfluxmongodb.adapters.repositories.models.WishlistModel;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface WishlistRepository extends ReactiveMongoRepository<WishlistModel, String> {

	@Query("{'customer.id': ?0}")
	Mono<WishlistModel> findByIdCustomer(final String idCustomer);
}
