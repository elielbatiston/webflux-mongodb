package com.elielbatiston.webfluxmongodb.usecases.wishlist.delete;

public record InputDeleteProductDTO(
	String idCustomer,
	String idProduct
) { }
