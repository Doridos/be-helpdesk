package cz.fel.cvut.behelpdesk.controller;

import cz.fel.cvut.behelpdesk.dto.DetailEmployeeDto;
import cz.fel.cvut.behelpdesk.dto.EmployeeUpdateDto;
import cz.fel.cvut.behelpdesk.enumeration.CategoryEnum;
import cz.fel.cvut.behelpdesk.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DetailEmployeeDto> getAllRequests() {
        return employeeService.getAllEmployees();
    }

    @GetMapping(value = "/{category}")
    public List<DetailEmployeeDto> getUsersByCategory(@PathVariable String category) {
        return employeeService.getEmployeesByCategory(CategoryEnum.valueOf(category.toUpperCase()));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{username}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeUpdateDto> updateSpecificEmployee(@PathVariable String username, @RequestBody EmployeeUpdateDto employeeUpdateDto) {
        return new ResponseEntity<>(employeeService.updateEmployee(username, employeeUpdateDto), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/add")
    public ResponseEntity<Integer> addEmployees() {
        return new ResponseEntity<>(employeeService.addEmployeesFromAD(), HttpStatus.OK);
    }
}
