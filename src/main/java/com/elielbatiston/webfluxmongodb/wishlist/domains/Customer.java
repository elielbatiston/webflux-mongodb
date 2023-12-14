package com.elielbatiston.webfluxmongodb.wishlist.domains;

public class Customer extends Entity {

	private String name;

	public Customer(String id, String name) {
		super(id);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void changeName(String name) {
		this.name = name;
	}
}
