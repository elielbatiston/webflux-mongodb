package com.elielbatiston.webfluxmongodb.domains.exceptions;

public class ObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ObjectNotFoundException(final String msg) {
		super(msg);
	}
}
