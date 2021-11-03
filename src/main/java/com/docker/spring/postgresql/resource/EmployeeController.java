package com.docker.spring.postgresql.resource;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.docker.spring.postgresql.exception.ResourceNotFoundException;
import com.docker.spring.postgresql.model.Employee;
import com.docker.spring.postgresql.repository.EmployeeJPARepository;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    
    

    @Autowired
    private EmployeeJPARepository repository;

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
    	System.out.println("Get all the employees...");
        logger.info("Get all the employees...");
        return new ResponseEntity( repository.findAll() , HttpStatus.OK);
        // return new ResponseEntity( repository. , HttpStatus.OK);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") int employeeId) throws ResourceNotFoundException {
        logger.info("Get employee by id...");
        Employee employee = repository.findById(employeeId).
                orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id:: " + employeeId));
        return ResponseEntity.ok().body(employee);

    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmployee(@Validated @RequestBody Employee employee) {
        logger.info("Insert employee...");
        //return new ResponseEntity( repository. , HttpStatus.OK);
     	return new ResponseEntity( repository.save(employee) , HttpStatus.OK);
    }
    
    @GetMapping("/testService")
    public ResponseEntity<String> testService(){
    	System.out.println("EmployeeController.testService()");
    	return new ResponseEntity("Hello there", HttpStatus.OK);
    	
    }
    
    @PostMapping("/addAll")
    public ResponseEntity<List<Employee>> addAllEmployees( @RequestBody List<Employee> employeeList){
    	System.out.println("EmployeeController.addAllEmployees()");
    	employeeList.forEach( ( element ) ->{
    		System.out.println("Employee : " + element );
    	});
    	return new ResponseEntity( repository.saveAll( employeeList ), HttpStatus.OK);
    }
    
    @PostMapping("/testtoadd")
    public ResponseEntity<String> testServiceToAdd( @RequestBody List<Employee> employeeList){
    	System.out.println("EmployeeController.testServiceToAdd()");
    	employeeList.forEach( ( element ) ->{ System.out.println("age : " + element.getAge() );});
    	return new ResponseEntity("Hello hi", HttpStatus.OK);
    }    

    @PutMapping("/employees")
    public ResponseEntity<Employee> EmployeeById(@RequestBody Employee updatedEmployee) throws ResourceNotFoundException {
        logger.info("Update employee : " + updatedEmployee.getId());
        Employee employee = repository.findById(updatedEmployee.getId()).
                orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id:: " + updatedEmployee.getId()));
        employee.setFirstName( updatedEmployee.getFirstName() );
        employee.setLastName( updatedEmployee.getLastName() );
        employee.setAge( updatedEmployee.getAge() );
        employee.setMobile( updatedEmployee.getMobile() );
        employee.setPosition( updatedEmployee.getPosition() );
        repository.save(employee);
        return new ResponseEntity( employee , HttpStatus.OK);
    }

    @DeleteMapping("/employees/{id}")
    public void deleteEmployee(@PathVariable(value = "id") int employeeId) throws ResourceNotFoundException {
        logger.info("Delete employee id : " + employeeId);
        System.out.println("delete employee");
        Employee employee = repository.findById( employeeId).
                orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id:: " + employeeId));
        repository.delete(employee);
    }
}
