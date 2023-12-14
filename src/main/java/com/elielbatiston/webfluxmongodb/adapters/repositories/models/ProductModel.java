package com.elielbatiston.webfluxmongodb.adapters.repositories.models;

import com.elielbatiston.webfluxmongodb.domains.Product;

import java.util.Objects;

public class ProductModel {

	private final String id;
	private final String name;
	private final Double price;

	public ProductModel(final String id, final String name, final Double price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}

	public Product toDomain() {
		return new Product(
			this.id,
			this.name,
			this.price
		);
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ProductModel that = (ProductModel) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public static ProductModel fromDomain(final Product product) {
		return new ProductModel(
			product.getId(),
			product.getName(),
			product.getPrice()
		);
	}
}
