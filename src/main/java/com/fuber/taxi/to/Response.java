package com.fuber.taxi.to;

public class Response {
	
	private String statusCode;
	private String statusMessage;
	private Object data;
	
	public Response(String statusCode, String statusMessage, Object data) {
		this.statusCode=statusCode;
		this.statusMessage=statusMessage;
		this.data=data;
	}

}
