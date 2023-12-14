package com.elielbatiston.webfluxmongodb.adapters.repositories;

import com.elielbatiston.webfluxmongodb.adapters.repositories.models.WishlistModel;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends ReactiveMongoRepository<WishlistModel, String> {}
