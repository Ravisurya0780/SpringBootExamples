package com.incture.example.dynamicFiltering;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class DynamicFilteringController {

	private MappingJacksonValue getMappingFilter(Set<String> filterList, Object obj, String filterKey) {
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(filterList);
		FilterProvider filters = new SimpleFilterProvider().addFilter(filterKey, filter);
		MappingJacksonValue mapping = new MappingJacksonValue(obj);
		mapping.setFilters(filters);
		return mapping;

	}

	@GetMapping("/dynamicFilter")
	public MappingJacksonValue getFilterBean() {
		EmployeeDynamicBean bean = new EmployeeDynamicBean("Ravi", "Kolar", "Karnataka", "563101");
		Set<String> filterListSet = new HashSet<String>();
		filterListSet.add("name");
		return getMappingFilter(filterListSet, bean, "DynamicFilter");

	}

	@GetMapping("/dynamicFilterList")
	public MappingJacksonValue getFilterBeanList() {
		List<EmployeeDynamicBean> listBean = Arrays.asList(
				new EmployeeDynamicBean("Ravi", "Kolar", "Karnataka", "563101"),
				new EmployeeDynamicBean("Surya", "Jadav", "Bangalore", "560032"));
		Set<String> filterListSet = new HashSet<String>();
		filterListSet.add("name");
		filterListSet.add("city");
		filterListSet.add("pincode");
		return getMappingFilter(filterListSet, listBean, "DynamicFilter");

	}

}
