package com.example.common;

/**
 * Enum of authentication types.
 */
public enum AuthenticationTypeEnum {

	NATIVE("NATIVE"), FACEBOOK("Facebook"), GMAIL("Gmail");

	private final String value;

	AuthenticationTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
