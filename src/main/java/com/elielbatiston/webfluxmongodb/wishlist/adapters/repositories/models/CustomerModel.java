package com.elielbatiston.webfluxmongodb.wishlist.adapters.repositories.models;

import com.elielbatiston.webfluxmongodb.wishlist.domains.Customer;
import org.springframework.data.mongodb.core.index.Indexed;

public class CustomerModel {

	@Indexed(unique = true)
	private final String id;
	private final String name;

	public CustomerModel(final String id, final String name) {
		this.id = id;
		this.name = name;
	}

	public Customer toDomain() {
		return new Customer(
			this.id,
			this.name
		);
	}

	public static CustomerModel fromDomain(final Customer customer) {
		return new CustomerModel(
			customer.getId(),
			customer.getName()
		);
	}
}
