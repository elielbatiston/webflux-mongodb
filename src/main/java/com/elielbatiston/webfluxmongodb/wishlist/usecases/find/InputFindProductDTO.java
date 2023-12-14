package com.elielbatiston.webfluxmongodb.wishlist.usecases.find;

public record InputFindProductDTO(
	String idCustomer,
	String idProduct
) { }
