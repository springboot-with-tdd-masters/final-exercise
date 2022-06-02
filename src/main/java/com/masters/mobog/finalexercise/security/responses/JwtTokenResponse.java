package com.masters.mobog.finalexercise.security.responses;


public class JwtTokenResponse {

	private final String token;

	public JwtTokenResponse(String jwtToken) {
		this.token = jwtToken;
	}

	public String getToken() {
		return token;
	}
	
}