package cz.fel.cvut.behelpdesk.controller;

import cz.fel.cvut.behelpdesk.dto.DetailRequestDto;
import cz.fel.cvut.behelpdesk.dto.InputRequestDto;
import cz.fel.cvut.behelpdesk.dto.RequestDto;
import cz.fel.cvut.behelpdesk.enumeration.CategoryEnum;
import cz.fel.cvut.behelpdesk.service.EmployeeService;
import cz.fel.cvut.behelpdesk.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/request")
public class RequestController {

    private final RequestService requestService;
    private final EmployeeService employeeService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RequestDto> addRequest(@RequestBody InputRequestDto inputRequestDto) {
        return new ResponseEntity<>(requestService.createRequest(inputRequestDto), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public DetailRequestDto getSpecificRequest(@PathVariable Long id) {
        return requestService.getRequestDetail(id);
    }

    @GetMapping(value = "/assigned/by/{username}",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DetailRequestDto> getRequestAssignedByEmployee(@PathVariable String username) {
        return requestService.getRequestsAssignedByEmployee(employeeService.findEmployeeById(username));
    }

    @GetMapping(value = "/assigned/to/{username}",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DetailRequestDto> getRequestAssignedToByEmployee(@PathVariable String username) {
        return requestService.getRequestsAssignedToEmployee(employeeService.findEmployeeById(username));
    }

    @GetMapping(value = "/category/{category}",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DetailRequestDto> getRequestByCategory(@PathVariable String category) {
        return requestService.getRequestsByCategory(CategoryEnum.valueOf(category.toUpperCase()));
    }

    @GetMapping(value = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DetailRequestDto> getAllRequests() {
        return requestService.getAllRequestsDetail();
    }

    @PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RequestDto> updateSpecificRequest(@PathVariable Long id, @RequestBody InputRequestDto inputRequestDto) {
        return new ResponseEntity<>(requestService.updateRequest(id, inputRequestDto), HttpStatus.OK);
    }
}
