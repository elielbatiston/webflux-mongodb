package com.elielbatiston.webfluxmongodb.wishlist.domains.exceptions;

public class ObjectNotFoundException extends RuntimeException {

	public ObjectNotFoundException(final String msg) {
		super(msg);
	}
}
