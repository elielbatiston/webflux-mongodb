package com.elielbatiston.webfluxmongodb.wishlist.usecases.add;

import java.util.Set;

public record OutputAddProductDTO(
	String id,
	CustomerDTO customer,
	Set<ProductDTO> products
) {

	public record CustomerDTO (
		String id,
		String name
	) {	}

	public record ProductDTO (
		String id,
		String name,
		Double price
	) { }
}
