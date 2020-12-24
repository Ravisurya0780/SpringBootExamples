package com.incture.example.versioning;

public class PersonBean2 {

	private PersonBean person;

	public PersonBean getPerson() {
		return person;
	}

	public void setPerson(PersonBean person) {
		this.person = person;
	}

	public PersonBean2(PersonBean person) {
		super();
		this.person = person;
	}

	public PersonBean2() {
	}

}
