package br.com.albergue.controller.dto;

public class TokenDto {

	private String token;
	private String type;

	public TokenDto(String token, String tipo) {
		this.token = token;
		this.type = tipo;
	}

	public String getToken() {
		return token;
	}

	public String getType() {
		return type;
	}
}
