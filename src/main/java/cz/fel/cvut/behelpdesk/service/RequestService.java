package cz.fel.cvut.behelpdesk.service;

import cz.fel.cvut.behelpdesk.dao.Employee;
import cz.fel.cvut.behelpdesk.dto.DetailRequestDto;
import cz.fel.cvut.behelpdesk.dto.InputRequestDto;
import cz.fel.cvut.behelpdesk.dao.Request;
import cz.fel.cvut.behelpdesk.dto.RequestDto;
import cz.fel.cvut.behelpdesk.exception.NotFoundException;
import cz.fel.cvut.behelpdesk.mapper.RequestDetailMapper;
import cz.fel.cvut.behelpdesk.mapper.RequestMapper;
import cz.fel.cvut.behelpdesk.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;
    private final RequestDetailMapper requestDetailMapper;
    public RequestDto createRequest(InputRequestDto inputRequestDto){
        Request requestToSave = requestMapper.toEntity(inputRequestDto);
        return requestMapper.toDto(requestRepository.save(requestToSave));
    }

    public RequestDto updateRequest(Long id, InputRequestDto inputRequestDto){
        Request requestToUpdate = findRequestById(id);
        requestMapper.partialUpdate(inputRequestDto, requestToUpdate);
        Request requestToSave = requestMapper.toEntity(inputRequestDto);
        return requestMapper.toDto(requestRepository.save(requestToSave));
    }

    public DetailRequestDto getRequestDetail(Long id){
       return requestDetailMapper.toDto(findRequestById(id));
    }

    public Request findRequestById(Long id){
        return requestRepository.findById(id).orElseThrow(() -> new NotFoundException("Request with id " + id + " not found"));
    }

}