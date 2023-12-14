package com.elielbatiston.webfluxmongodb.wishlist.usecases.delete;

public record InputDeleteProductDTO(
	String idCustomer,
	String idProduct
) { }
