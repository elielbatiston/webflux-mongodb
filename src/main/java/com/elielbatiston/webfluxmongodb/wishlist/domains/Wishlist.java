package com.elielbatiston.webfluxmongodb.wishlist.domains;

import java.util.HashSet;
import java.util.Set;

public class Wishlist extends Entity {

	private final Customer customer;
	private Set<Product> products;

	public Wishlist(final String id, final Customer customer) {
		super(id);
		this.customer = customer;
	}

	public Wishlist(final Customer customer) {
		this(null, customer);
	}

	public Customer getCustomer() {
		return customer;
	}

	public Set<Product> getProducts() {
		if (this.products == null) {
			this.products = new HashSet<>();
		}
		return this.products;
	}

	public void addProduct(final Product product) {
		if (this.products == null) {
			this.products = new HashSet<>();
		}
		this.products.add(product);
	}

	public void removeProduct(final Product product) {
		this.getProducts().remove(product);
	}
}
