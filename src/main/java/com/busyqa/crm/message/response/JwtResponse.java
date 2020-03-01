package com.busyqa.crm.message.response;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;

public class JwtResponse {
	List<String> username;
	private String token;
	private String type = "Bearer";
	private Collection<? extends GrantedAuthority> authorities;

	public JwtResponse(String accessToken, List<String> username, Collection<? extends GrantedAuthority> authorities) {
		this.token = accessToken;
		this.username = username;
		this.authorities = authorities;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public List<String> getUsername() {
		return username;
	}

	public void setUsername(List<String> username) {
		this.username = username;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
}
