package com.elielbatiston.webfluxmongodb.wishlist.domains;

import java.util.Objects;

public abstract class Entity {
	private String id;

	public Entity(final String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (!(o instanceof Entity entity)) return false;
		return Objects.equals(id, entity.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
