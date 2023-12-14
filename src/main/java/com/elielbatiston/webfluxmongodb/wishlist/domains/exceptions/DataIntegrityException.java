package com.elielbatiston.webfluxmongodb.wishlist.domains.exceptions;

public class DataIntegrityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DataIntegrityException(final String msg) {
		super(msg);
	}
}
