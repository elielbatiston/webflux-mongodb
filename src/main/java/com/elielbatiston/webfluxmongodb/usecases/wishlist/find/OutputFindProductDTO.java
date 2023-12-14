package com.elielbatiston.webfluxmongodb.usecases.wishlist.find;

import com.elielbatiston.webfluxmongodb.domains.Customer;
import com.elielbatiston.webfluxmongodb.domains.Product;

public record OutputFindProductDTO(
	OutputFindAProductCustomerDTO customer,
	OutputFindAProductProductDTO product
) {

	public record OutputFindAProductCustomerDTO(
		String id,
		String name
	) {

		public static OutputFindAProductCustomerDTO fromDomain(final Customer customer) {
			return new OutputFindAProductCustomerDTO(
				customer.getId(),
				customer.getName()
			);
		}
	}

	public record OutputFindAProductProductDTO(
		String id,
		String name,
		Double price
	) {

		public static OutputFindAProductProductDTO fromDomain(final Product product) {
			return new OutputFindAProductProductDTO(
				product.getId(),
				product.getName(),
				product.getPrice()
			);
		}
	}
}
