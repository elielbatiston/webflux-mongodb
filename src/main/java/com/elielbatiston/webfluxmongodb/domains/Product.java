package com.elielbatiston.webfluxmongodb.domains;

public class Product extends Entity {

	private String name;
	private Double price;

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
