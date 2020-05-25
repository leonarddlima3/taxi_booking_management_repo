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

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
