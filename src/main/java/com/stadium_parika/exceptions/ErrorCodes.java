package com.stadium_parika.exceptions;

public enum ErrorCodes {
	
	VEHICLETYPE_NOT_FOUND(1000),
	VEHICLETYPE_NOT_VALID(1001),
	VEHICLETYPE_ALREADY_EXISTS(1002),
	VEHICLETYPE_IN_USE(1003),
	
	PARKING_NOT_FOUND(2000),
	PARKING_NOT_VALID(2001),
	PARKING_ALREADY_EXISTS(2002),
	PARKING_IN_USE(2003),
	
	// Liste des exception techniques
	UNKNOWN_CONTEXT(14001)
	;
	
	private int code;
	
	ErrorCodes(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
}
