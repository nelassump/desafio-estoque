package br.com.desafio.estoque.exception;

import java.util.Date;

public class ErrorMessage {
	private final int code;
	private final String status;
	private final String error;
	private final Date datetime;
	private final String developeMessage;
	
	public ErrorMessage(int code, String status, String error, Date datetime, String developeMessage) {
		this.code = code;
		this.status = status;
		this.error = error;
		this.datetime = datetime;
		this.developeMessage = developeMessage;
	}
	
	public int getCode() {
		return code;
	}
	public String getdevelopeMessage() {
		return developeMessage;
	}
	public String getStatus() {
		return status;
	}
	public Date getDatetime() {
		return datetime;
	}
	public String geterror() {
		return error;
	}
}
