package com.fuber.taxi.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("static")
public final class TaxiConstant {
	
	private TaxiConstant() {}
	
	public static final int MAX_DISTANCE_THRESHOLD = 10;

}
