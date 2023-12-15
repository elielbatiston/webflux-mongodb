package com.elielbatiston.webfluxmongodb.wishlist.usecases.findallwishlistbyids;

import com.elielbatiston.webfluxmongodb.wishlist.domains.Wishlist;
import com.elielbatiston.webfluxmongodb.wishlist.domains.gateways.WishlistGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@Service
public class FindAllWishlistByIdsUseCase {

	private static final Logger log = LoggerFactory.getLogger(FindAllWishlistByIdsUseCase.class);
	public static final int CONCURRENCY = 2000;

	private final WishlistGateway gateway;

	public FindAllWishlistByIdsUseCase(final WishlistGateway gateway) {
		this.gateway = gateway;
	}

	public Flux<Wishlist> execute(final InputFindAllWishlistByIds input) {
		final List<String> _ids = Arrays.asList(input.ids().split(","));
		log.info("Collecting {} wishlist", _ids.size());
		return Flux.fromIterable(_ids)
			.flatMap(gateway::findAllByWishlistId, CONCURRENCY);
	}
}
