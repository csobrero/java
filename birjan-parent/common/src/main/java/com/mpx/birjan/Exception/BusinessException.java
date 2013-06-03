package com.mpx.birjan.Exception;

import javax.xml.ws.WebFault;

@WebFault(name = "BusinessException") 
public class BusinessException extends Exception {

	private static final long serialVersionUID = -705011064087233469L;

	public BusinessException() {
		super();
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

}
