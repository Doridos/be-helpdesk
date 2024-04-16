package cz.fel.cvut.behelpdesk.controller;

import cz.fel.cvut.behelpdesk.dao.EmailRequest;
import cz.fel.cvut.behelpdesk.dto.DetailEmployeeDto;
import cz.fel.cvut.behelpdesk.dto.DetailRequestDto;
import cz.fel.cvut.behelpdesk.dto.InputRequestDto;
import cz.fel.cvut.behelpdesk.dto.RequestDto;
import cz.fel.cvut.behelpdesk.enumeration.CategoryEnum;
import cz.fel.cvut.behelpdesk.service.EmailService;
import cz.fel.cvut.behelpdesk.service.EmployeeService;
import cz.fel.cvut.behelpdesk.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/request")
public class RequestController {

    private final RequestService requestService;
    private final EmployeeService employeeService;
    private final EmailService emailService;

    @PostMapping(value = "/post", consumes = MediaType.APPLICATION_JSON_VALUE)
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

    @PreAuthorize("hasRole('ADMIN') || hasRole('MANAGER') || hasRole('SOLVER')")
    @GetMapping(value = "/assigned/to/{username}",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DetailRequestDto> getRequestAssignedToByEmployee(@PathVariable String username) {
        return requestService.getRequestsAssignedToEmployee(employeeService.findEmployeeById(username));
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('MANAGER') || hasRole('SOLVER')")
    @GetMapping(value = "/category/{category}",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DetailRequestDto> getRequestByCategory(@PathVariable String category) {
        return requestService.getRequestsByCategory(CategoryEnum.valueOf(category.toUpperCase()));
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('MANAGER') || hasRole('SOLVER')")
    @PostMapping(value = "/categories", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DetailRequestDto> getRequestsByCategories(@RequestBody List<CategoryEnum> categories) {
        return requestService.getRequestsByCategories(categories);
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('MANAGER')")
    @GetMapping(value = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DetailRequestDto> getAllRequests() {
        return requestService.getAllRequestsDetail();
    }

    @PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RequestDto> updateSpecificRequest(@PathVariable Long id, @RequestBody InputRequestDto inputRequestDto) {
        return new ResponseEntity<>(requestService.updateRequest(id, inputRequestDto), HttpStatus.OK);
    }

    @GetMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
    public String index() {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setMessage("");
        emailRequest.setSubject("");
        Set<String> recipients = Set.of("");
        emailRequest.setRecipients(recipients);
            emailService.sendEmail(emailRequest);
            return "Email sent successfully";
    }
}
