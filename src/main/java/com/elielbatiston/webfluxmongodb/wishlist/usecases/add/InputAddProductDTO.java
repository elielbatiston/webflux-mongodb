package com.elielbatiston.webfluxmongodb.wishlist.usecases.add;

import com.elielbatiston.webfluxmongodb.wishlist.domains.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record InputAddProductDTO(
	@Valid
	@NotNull(message = "O campo não pode ser nulo")
	CustomerDTO customer,

	@Valid
	@NotNull(message = "O campo não pode ser nulo")
	ProductDTO product
) {

	public record CustomerDTO (
		@NotBlank(message="O campo não pode ser nulo ou branco")
		String id,

		@Size(min=5, max=80, message="O tamanho do campo deve ser entre 5 e 80")
		@NotBlank(message="O campo não pode ser nulo")
		String name
	) {

	}

	public record ProductDTO (
		@NotBlank(message="O campo não pode ser nulo ou branco")
		String id,

		@Size(min=5, max=80, message="O tamanho do campo deve ser entre 5 e 80")
		@NotBlank(message="O campo não pode ser nulo ou branco")
		String name,

		@Positive(message="O campo deve ser maior ou igual a 0")
		Double price
	) {

		public Product toDomain() {
			return new Product(
				this.id,
				this.name,
				this.price
			);
		}
	}
}
