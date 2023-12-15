package com.elielbatiston.webfluxmongodb.wishlist.domains.exceptions;

public class DataIntegrityException extends RuntimeException {

	public DataIntegrityException(final String msg) {
		super(msg);
	}
}
