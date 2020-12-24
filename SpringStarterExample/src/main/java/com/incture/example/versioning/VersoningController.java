package com.incture.example.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersoningController {

	// Versioning by Request Mapping
	@GetMapping("/v1/person")
	public PersonBean getPerson() {
		return new PersonBean("Ravi", "kolar", "Hindu");
	}

	@GetMapping("/v2/person")
	public PersonBean2 getPerson2() {
		return new PersonBean2(new PersonBean("Ravi", "kolar", "Hindu"));
	}

	// Versioning by Request Param
	@GetMapping(value = "/person/param", params = "version=1")
	public PersonBean getPersonParam1() {
		return new PersonBean("Ravi", "kolar", "Hindu");
	}

	@GetMapping(value = "/person/param", params = "version=2")
	public PersonBean2 getPersonParam2() {
		return new PersonBean2(new PersonBean("Ravi", "kolar", "Hindu"));
	}

	// Versioning by Request Header
	@GetMapping(value = "/person/header", headers = "API_VERSION=1")
	public PersonBean getPersonHeader1() {
		return new PersonBean("Ravi", "kolar", "Hindu");
	}

	@GetMapping(value = "/person/header", headers = "API_VERSION=2")
	public PersonBean2 getPersonHeader2() {
		return new PersonBean2(new PersonBean("Ravi", "kolar", "Hindu"));
	}

	// Versioning by produces
	@GetMapping(value = "/person/produce", produces = "application/vnd.company.app-v1+json")
	public PersonBean getPersonProduces1() {
		return new PersonBean("Ravi", "kolar", "Hindu");
	}

	@GetMapping(value = "/person/produce", produces = "application/vnd.company.app-v2+json")
	public PersonBean2 getPersonProduces2() {
		return new PersonBean2(new PersonBean("Ravi", "kolar", "Hindu"));
	}

}
