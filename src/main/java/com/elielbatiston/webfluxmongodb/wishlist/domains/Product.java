package com.elielbatiston.webfluxmongodb.wishlist.domains;

public class Product extends Entity {

	private final String name;
	private final Double price;

	public Product(final String id, final String name, final Double price) {
		super(id);
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public Double getPrice() {
		return price;
	}
}
