package com.incture.example.staticfiltering;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilteringController {
	
	@GetMapping("/filterEmployee")
	public EmployeeBean filter()
	{
		return new EmployeeBean("Ravi","Kolar","Karnataka","563101");
				
				
	}

}
