package cz.fel.cvut.behelpdesk.controller;

import cz.fel.cvut.behelpdesk.dao.Employee;
import cz.fel.cvut.behelpdesk.dto.*;
import cz.fel.cvut.behelpdesk.enumeration.CategoryEnum;
import cz.fel.cvut.behelpdesk.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    @GetMapping(value = "/user/{username}",produces = MediaType.APPLICATION_JSON_VALUE)
    public DetailEmployeeDto getEmployeeByUsername(@PathVariable String username) {
        return employeeService.getEmployeeDetailByUsername(username);

    }
    @GetMapping(value = "/{category}")
    public List<DetailEmployeeDto> getUsersByCategory(@PathVariable String category) {
        return employeeService.getEmployeesByCategory(CategoryEnum.valueOf(category.toUpperCase()));
    }
    @PutMapping(value = "/{username}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeUpdateDto> updateSpecificEmployee(@PathVariable String username, @RequestBody EmployeeUpdateDto employeeUpdateDto) {
        return new ResponseEntity<>(employeeService.updateEmployee(username, employeeUpdateDto), HttpStatus.OK);
    }
    @PostMapping(value = "/add")
    public ResponseEntity<Integer> addEmployees() {
        return new ResponseEntity<>(employeeService.addEmployeesFromAD(), HttpStatus.OK);
    }
}
