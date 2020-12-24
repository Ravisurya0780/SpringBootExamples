package com.incture.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incture.example.exception.RecordNotFoundException;
import com.incture.example.model.EmployeeEntity;
import com.incture.example.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository repository;

	public List<EmployeeEntity> getAllEmployees() {
		List<EmployeeEntity> employeeList = repository.findAll();
		if (employeeList.size() > 0) {
			return employeeList;
		} else {
			return new ArrayList<EmployeeEntity>();
		}
	}

	public EmployeeEntity getEmployeeById(Long id) throws RecordNotFoundException {
		Optional<EmployeeEntity> employee = repository.findById(id);

		if (employee.isPresent()) {
			return employee.get();
		} else {
			throw new RecordNotFoundException("No employee record exist for given id");
		}
	}

	public EmployeeEntity createOrUpdateEmployee(EmployeeEntity entity) throws RecordNotFoundException {
		System.err.println("createOrUpdateEmployee ID "+entity.getId());
		if (null != entity.getId()) {
			Optional<EmployeeEntity> employee = repository.findById(entity.getId());

			if (employee.isPresent()) {
				System.err.println("createOrUpdateEmployee ID if condition "+employee.isPresent());
				EmployeeEntity newEntity = employee.get();
				newEntity.setEmail(entity.getEmail());
				newEntity.setFirstName(entity.getFirstName());
				newEntity.setLastName(entity.getLastName());
				newEntity.setCompany(entity.getCompany());
				newEntity.setDateOfBirth(entity.getDateOfBirth());
				newEntity.setDesignation(entity.getDesignation());
				newEntity.setPhone(entity.getPhone());
				newEntity = repository.save(newEntity);

				return newEntity;
			}
			else
			{
				List<EmployeeEntity> employeeList = new ArrayList<>();
				Long count = null;
				employeeList = repository.findAll();
				if(employeeList!=null)
				{
					EmployeeEntity employee2= repository.getRecordByDesc().get(0);
					count=employee2.getId()+1;
				}
				entity.setId(count);
				System.err.println("createOrUpdateEmployee ID if else "+entity.getId());
				entity = repository.save(entity);
				return entity;
			}
		} else {
			List<EmployeeEntity> employeeList = new ArrayList<>();
			Long count = null;
			employeeList = repository.findAll();
			if(employeeList!=null)
			{
				EmployeeEntity employee= repository.getRecordByDesc().get(0);
				count=employee.getId()+1;
			}
			entity.setId(count);
			System.err.println("createOrUpdateEmployee ID else "+entity.getId());
			System.err.println("createOrUpdateEmployee ID dateofBirth "+entity.getDateOfBirth());
			entity = repository.save(entity);
			return entity;
		}

	}

	public void deleteEmployeeById(Long id) throws RecordNotFoundException {
		Optional<EmployeeEntity> employee = repository.findById(id);

		if (employee.isPresent()) {
			repository.deleteById(id);
		} else {
			throw new RecordNotFoundException("No employee record exist for given id");
		}
	}
}