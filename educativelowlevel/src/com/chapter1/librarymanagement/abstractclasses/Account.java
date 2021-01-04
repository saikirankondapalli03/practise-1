package com.chapter1.librarymanagement.abstractclasses;

import com.chapter1.librarymanagement.Person;
import com.chapter1.librarymanagement.enums.AccountStatus;

public abstract class Account {
	private String password;
	private String id;
	private AccountStatus status;
	private Person person;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AccountStatus getStatus() {
		return status;
	}

	public void setStatus(AccountStatus status) {
		this.status = status;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public abstract boolean resetPassword();
}
