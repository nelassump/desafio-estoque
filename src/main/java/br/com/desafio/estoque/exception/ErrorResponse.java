package br.com.desafio.estoque.exception;

import java.util.List;

public class ErrorResponse {
	private final int code;
	private final String status;
    private final String message;
    private final List<ErrorObject> errors;

	public ErrorResponse(int code, String status, String message, List<ErrorObject> errors) {
		this.code = code;
		this.status = status;
		this.message = message;
		this.errors = errors;
	}
	
	public String getMessage() {
		return message;
	}
	public int getCode() {
		return code;
	}
	public String getStatus() {
		return status;
	}
	public List<ErrorObject> getErrors() {
		return errors;
	}
}
