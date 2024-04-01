package cz.fel.cvut.behelpdesk.controller;

import cz.fel.cvut.behelpdesk.dao.Request;
import cz.fel.cvut.behelpdesk.dto.DetailRequestDto;
import cz.fel.cvut.behelpdesk.dto.InputRequestDto;
import cz.fel.cvut.behelpdesk.dto.RequestDto;
import cz.fel.cvut.behelpdesk.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/request")
public class RequestController {

    private final RequestService requestService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RequestDto> addRequest(@RequestBody InputRequestDto inputRequestDto) {
        return new ResponseEntity<>(requestService.createRequest(inputRequestDto), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public DetailRequestDto getSpecificRequest(@PathVariable Long id) {
        return requestService.getRequestDetail(id);
    }
    @PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RequestDto> updateSpecificRequest(@PathVariable Long id, @RequestBody InputRequestDto inputRequestDto) {
        return new ResponseEntity<>(requestService.updateRequest(id, inputRequestDto), HttpStatus.OK);
    }
}
