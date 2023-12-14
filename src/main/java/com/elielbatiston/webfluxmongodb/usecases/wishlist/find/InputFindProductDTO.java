package com.elielbatiston.webfluxmongodb.usecases.wishlist.find;

public record InputFindProductDTO(
	String idCustomer,
	String idProduct
) { }
