package com.example.common.builder.domain;

import java.util.UUID;

import com.example.entities.domain.PhoneType;

public class PhoneTypeBuilder {

	private PhoneType entity = new PhoneType();

	public PhoneTypeBuilder() {}

	public PhoneTypeBuilder(PhoneType entity) {
		this.entity = entity;
	}

	private Long generateUUID() {
		return (long) UUID.randomUUID().hashCode();
	}

	public PhoneTypeBuilder any() {
		Long id = generateUUID();
		this.withId(id);
		this.withText("");
		return this;
	}

	public PhoneType build() {
		return this.entity;
	}

	public PhoneTypeBuilder withId(Long phoneTypeId) {
		this.entity.setPhoneTypeId(phoneTypeId);
		return this;
	}

	public PhoneTypeBuilder withText(String phoneTypeText) {
		this.entity.setPhoneTypeText(phoneTypeText);
		return this;
	}

}