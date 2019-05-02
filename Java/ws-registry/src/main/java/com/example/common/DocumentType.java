package com.example.common;

/**
 * Enum of document types.
 */
public enum DocumentType {

	RG(1), PASSPORT(2), BIRTH_CERTIFICATE(3);

	private final int value;

	DocumentType(int value) {
		this.value = value;
	}

	public Long getValue() {
		return Long.valueOf(value);
	} 

}
