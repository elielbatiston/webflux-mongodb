package com.elielbatiston.webfluxmongodb.configs;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "wishlist.products")
public class WishlistProductsProperties {

	@NotBlank
	private final Integer maximumLimitAllowed;

	public WishlistProductsProperties(final Integer maximumLimitAllowed) {
		this.maximumLimitAllowed = maximumLimitAllowed;
	}

	public Integer getMaximumLimitAllowed() {
		return maximumLimitAllowed;
	}
}
