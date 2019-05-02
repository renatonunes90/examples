package com.example.common;

/**
 * Enum of CRUD operations.
 */
public enum CRUDOperation {

	CREATE(1), READ(2), UPDATE(3), DELETE(4);

	private final int value;

	CRUDOperation(int value) {
		this.value = value;
	}

	public Long getValue() {
		return Long.valueOf(value);
	}

}
