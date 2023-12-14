package com.elielbatiston.webfluxmongodb.adapters.repositories.models;

import com.elielbatiston.webfluxmongodb.domains.Wishlist;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Document(collection = "wishlist")
public class WishlistModel {

	@Id
	@Field("_id")
	private final String id;
	private final CustomerModel customer;
	private final Set<ProductModel> products;

	public WishlistModel(final String id, final CustomerModel customer, final Set<ProductModel> products) {
		this.id = id;
		this.customer = customer;
		this.products = products;
	}

	public Wishlist toDomain() {
		final Wishlist wishlist = new Wishlist(
			this.id,
			this.customer.toDomain()
		);
		this.products
			.forEach(it -> wishlist.addProduct(it.toDomain()));
		return wishlist;
	}

	public static WishlistModel fromDomain(final Wishlist wishlist) {
		final String id = Optional.ofNullable(wishlist.getId()).orElse(UUID.randomUUID().toString());
		return new WishlistModel(
			id,
			CustomerModel.fromDomain(wishlist.getCustomer()),
			wishlist.getProducts().stream()
				.map(ProductModel::fromDomain)
				.collect(Collectors.toSet())
		);
	}
}
