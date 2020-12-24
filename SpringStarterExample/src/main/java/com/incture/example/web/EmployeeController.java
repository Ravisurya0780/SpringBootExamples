package com.incture.example.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.incture.example.exception.RecordNotFoundException;
import com.incture.example.model.EmployeeEntity;
import com.incture.example.service.EmployeeService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@Validated
public class EmployeeController {
	@Autowired
	EmployeeService service;
	final ModelAndView model = new ModelAndView();

	@GetMapping("employees")
	public String index(Model model) {
		model.addAttribute("employees", service.getAllEmployees());
		return "employeeList";
	}

	@RequestMapping("/edit")
	public String getAllEmployees(Model model) {
		model.addAttribute("employee", new EmployeeEntity());
		return "add-edit-employee";
	}

	@PostMapping("/createEmployeeOld")
	// @RequestMapping("/createEmployee")
	// @ResponseBody
	public ResponseEntity<EmployeeEntity> createOrUpdateEmployee(EmployeeEntity employee, Model model)
			throws RecordNotFoundException {
		model.addAttribute("createEmployee", new EmployeeEntity());
		System.err.println("Create Employee Email " + employee.getEmail());
		System.err.println("Create Employee First " + employee.getFirstName());
		System.err.println("Create Employee Last " + employee.getLastName());
		// model.getAttribute("employee");
		// model.addAttribute(attributeName, attributeValue);
		EmployeeEntity updated = service.createOrUpdateEmployee(employee);

		return new ResponseEntity<EmployeeEntity>(updated, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/edit/{id}")
	public String getEmployeeById(@PathVariable("id") Long id, Model model) throws RecordNotFoundException {
		EmployeeEntity entity = service.getEmployeeById(id);
		model.addAttribute("employee", entity);
		return "add-edit-employee";
	}

	@PostMapping("/createEmployee")
	public String createOrUpdateEmployeeLatest(EmployeeEntity employee, Model model) throws RecordNotFoundException {
		EmployeeEntity updated = service.createOrUpdateEmployee(employee);
		model.addAttribute("submit", updated);
		model.addAttribute("employees", service.getAllEmployees());
		return "employeeList";
	}

	@GetMapping("/delete/{id}")
	public String deleteEmployeeById(@PathVariable("id") Long id, Model model) throws RecordNotFoundException {
		service.deleteEmployeeById(id);
		model.addAttribute("employees", service.getAllEmployees());
		return "employeeList";
	}

	@PostMapping("/createEmp")
	public ResponseEntity<Object> createEmployee(@Valid @RequestBody EmployeeEntity emp)
			throws RecordNotFoundException {
		emp = service.createOrUpdateEmployee(emp);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(emp.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(ConstraintViolationException.class)
	public Map<String, String> handleConstraintViolation(ConstraintViolationException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getConstraintViolations().forEach(cv -> {
			errors.put("message", cv.getMessage());
			errors.put("path", (cv.getPropertyPath()).toString());
		});

		return errors;
	}

	/*
	 * @ResponseStatus(HttpStatus.BAD_REQUEST)
	 * 
	 * @ExceptionHandler(ConstraintViolationException.class) public Map<String,
	 * String> handleBadRequest(BadRequest ex) { Map<String, String> errors = new
	 * HashMap<>(); ex.get.forEach(cv -> { errors.put("message", cv.getMessage());
	 * errors.put("path", (cv.getPropertyPath()).toString()); });
	 * 
	 * return errors; }
	 */
	
	/*
	 * @GetMapping("/{id}") public ResponseEntity<EmployeeEntity>
	 * getEmployeeById(@PathVariable("id") Long id) throws RecordNotFoundException {
	 * EmployeeEntity entity = service.getEmployeeById(id);
	 * 
	 * return new ResponseEntity<EmployeeEntity>(entity, new HttpHeaders(),
	 * HttpStatus.OK); }
	 * 
	 * @PostMapping public ResponseEntity<EmployeeEntity>
	 * createOrUpdateEmployee(EmployeeEntity employee) throws
	 * RecordNotFoundException { EmployeeEntity updated =
	 * service.createOrUpdateEmployee(employee); return new
	 * ResponseEntity<EmployeeEntity>(updated, new HttpHeaders(), HttpStatus.OK); }
	 * 
	 * @DeleteMapping("/{id}") public HttpStatus
	 * deleteEmployeeById(@PathVariable("id") Long id) throws
	 * RecordNotFoundException { service.deleteEmployeeById(id); return
	 * HttpStatus.FORBIDDEN; }
	 */

	// Method to create the pdf report via jasper framework.
	/*
	 * @GetMapping(value = "/exportPdf/{id}") public ModelAndView viewReport() {
	 * log.info("Preparing the pdf report via jasper."); try {
	 * createPdfReport(eservice.findAll());
	 * log.info("File successfully saved at the given path."); } catch (final
	 * Exception e) {
	 * log.error("Some error has occured while preparing the employee pdf report.");
	 * e.printStackTrace(); } // Returning the view name as the index page for ease.
	 * model.setViewName("welcome"); return model;
	 */
	@GetMapping(value = "/exportPdf/{id}")
	public void viewReport(@PathVariable("id") Long id, HttpServletResponse response)
			throws RecordNotFoundException {
		try {
			response.setContentType("application/x-download");
			response.setHeader("Content-Disposition",
					String.format("attachment; filename=\"" + service.getEmployeeById(id).getFirstName() + ".pdf\""));
			createPdfReport(service.getEmployeeById(id), response);
		
		} catch (final Exception e) {

			e.printStackTrace();
		}
	}
	
	@GetMapping(value = "/exportAll")
	public void viewReport(HttpServletResponse response)
			throws RecordNotFoundException {
		try {
			response.setContentType("application/x-download");
			response.setHeader("Content-Disposition",
					String.format("attachment; filename=\"EmployeesReport.pdf\""));
			createPdfReport(service.getAllEmployees(), response);
		
		} catch (final Exception e) {

			e.printStackTrace();
		}
	}

	// Method to create the pdf file using the employee list datasource.
	private void createPdfReport(final EmployeeEntity employees, HttpServletResponse response)
			throws JRException, IOException {
		// Fetching the .jrxml file from the resources folder.
		final InputStream stream = this.getClass().getResourceAsStream("/emp_rep.jrxml");
		ArrayList<EmployeeEntity> list = new ArrayList<>();
		list.add(employees);
		// Compile the Jasper report from .jrxml to .japser
		final JasperReport report = JasperCompileManager.compileReport(stream);

		// Fetching the employees from the data source.
		final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(list);
		// Adding the additional parameters to the pdf.
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("createdBy", "Ravi Kumar P");
		// Filling the report with the employee data and additional parameters
		// information.
		final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);
		OutputStream out = response.getOutputStream();
		// Users can change as per their project requrirements or can take it as request
		// input requirement.
		// For simplicity, this tutorial will automatically place the file under the
		// "c:" drive.
		// If users want to download the pdf file on the browser, then they need to use
		// the "Content-Disposition" technique.
		final String filePath = "\\";
		// Export the report to a PDF file.

		JasperExportManager.exportReportToPdfStream(print, out);
		// JasperExportManager.exportReportToPdfFile(print, filePath +
		// "Employee_report.pdf");
	}
	
	// Method to create the pdf file using the employee list datasource.
		private void createPdfReport(final List<EmployeeEntity> list, HttpServletResponse response)
				throws JRException, IOException {
			// Fetching the .jrxml file from the resources folder.
			final InputStream stream = this.getClass().getResourceAsStream("/emp_rep.jrxml");
			// Compile the Jasper report from .jrxml to .japser
			final JasperReport report = JasperCompileManager.compileReport(stream);

			// Fetching the employees from the data source.
			final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(list);
			// Adding the additional parameters to the pdf.
			final Map<String, Object> parameters = new HashMap<>();
			parameters.put("createdBy", "Ravi Kumar P");
			// Filling the report with the employee data and additional parameters
			// information.
			final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);
			OutputStream out = response.getOutputStream();
			// Users can change as per their project requrirements or can take it as request
			// input requirement.
			// For simplicity, this tutorial will automatically place the file under the
			// "c:" drive.
			// If users want to download the pdf file on the browser, then they need to use
			// the "Content-Disposition" technique.
			final String filePath = "\\";
			// Export the report to a PDF file.

			JasperExportManager.exportReportToPdfStream(print, out);
			// JasperExportManager.exportReportToPdfFile(print, filePath +
			// "Employee_report.pdf");
		}

}