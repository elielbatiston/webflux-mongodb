package com.elielbatiston.webfluxmongodb.configs;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(
	WishlistProductsProperties.class
)
public class WishlistConfig {

	private final WishlistProductsProperties wishlistProductsProperties;

	public WishlistConfig(final WishlistProductsProperties wishlistProductsProperties) {
		this.wishlistProductsProperties = wishlistProductsProperties;
	}

	public WishlistProductsProperties getWishlistProductsProperties() {
		return this.wishlistProductsProperties;
	}
}
