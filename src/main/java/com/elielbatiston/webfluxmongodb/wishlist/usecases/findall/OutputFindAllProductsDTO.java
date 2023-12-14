package com.elielbatiston.webfluxmongodb.wishlist.usecases.findall;

import com.elielbatiston.webfluxmongodb.wishlist.domains.Wishlist;

import java.util.Set;
import java.util.stream.Collectors;

public record OutputFindAllProductsDTO(
	OutputFindAllCustomerProductCustomerDTO customer,
	Set<OutputFindAllCustomerProductProductDTO> products
) {

	public static OutputFindAllProductsDTO fromDomain(final Wishlist wishlist) {
		final OutputFindAllCustomerProductCustomerDTO customer = new OutputFindAllCustomerProductCustomerDTO(
			wishlist.getCustomer().getId(),
			wishlist.getCustomer().getName()
		);
		final Set<OutputFindAllCustomerProductProductDTO> products = wishlist.getProducts().stream()
			.map(it -> new OutputFindAllCustomerProductProductDTO(
				it.getId(),
				it.getName(),
				it.getPrice()
			))
			.collect(Collectors.toSet());

		return new OutputFindAllProductsDTO(
			customer,
			products
		);
	}

	public record OutputFindAllCustomerProductCustomerDTO (
		String id,
		String name
	) { }

	public record OutputFindAllCustomerProductProductDTO (
		String id,
		String name,
		Double price
	) { }
}
