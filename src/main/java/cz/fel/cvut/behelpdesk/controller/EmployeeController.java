package cz.fel.cvut.behelpdesk.controller;

import cz.fel.cvut.behelpdesk.dao.Employee;
import cz.fel.cvut.behelpdesk.dto.*;
import cz.fel.cvut.behelpdesk.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    @GetMapping(value = "/{username}",produces = MediaType.APPLICATION_JSON_VALUE)
    public DetailEmployeeDto getEmployeeByUsername(@PathVariable String username) {
        return employeeService.getEmployeeDetailByUsername(username);

    }
    @PutMapping(value = "/{username}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InputEmployeeCategoriesDto> updateSpecificEmployee(@PathVariable String username, @RequestBody InputEmployeeCategoriesDto inputEmployeeCategoriesDto) {
        return new ResponseEntity<>(employeeService.updateEmployee(username, inputEmployeeCategoriesDto), HttpStatus.OK);
    }
    @PostMapping(value = "/add")
    public ResponseEntity<Integer> addEmployees() {
        return new ResponseEntity<>(  employeeService.addEmployeesFromAD(), HttpStatus.OK);
    }
}
