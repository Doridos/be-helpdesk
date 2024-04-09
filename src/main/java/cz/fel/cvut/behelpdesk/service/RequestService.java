package cz.fel.cvut.behelpdesk.service;

import cz.fel.cvut.behelpdesk.dao.Employee;
import cz.fel.cvut.behelpdesk.dto.DetailRequestDto;
import cz.fel.cvut.behelpdesk.dto.InputRequestDto;
import cz.fel.cvut.behelpdesk.dao.Request;
import cz.fel.cvut.behelpdesk.dto.RequestDto;
import cz.fel.cvut.behelpdesk.enumeration.CategoryEnum;
import cz.fel.cvut.behelpdesk.exception.NotFoundException;
import cz.fel.cvut.behelpdesk.mapper.RequestDetailMapper;
import cz.fel.cvut.behelpdesk.mapper.RequestMapper;
import cz.fel.cvut.behelpdesk.repository.RequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;
    private final RequestDetailMapper requestDetailMapper;

    @Transactional
    public RequestDto createRequest(InputRequestDto inputRequestDto){
        Request requestToSave = requestMapper.toEntity(inputRequestDto);
        return requestMapper.toDto(requestRepository.save(requestToSave));
    }

    @Transactional
    public RequestDto updateRequest(Long id, InputRequestDto inputRequestDto){
        Request requestToUpdate = findRequestById(id);
        requestToUpdate = requestMapper.partialUpdate(inputRequestDto, requestToUpdate);
        return requestMapper.toDto(requestRepository.save(requestToUpdate));
    }

    public List<DetailRequestDto> getRequestsByCategory(CategoryEnum category){
        return requestRepository.findByRequestCategory(category).stream().map(requestDetailMapper::toDto).toList();
    }

    public DetailRequestDto getRequestDetail(Long id){
       return requestDetailMapper.toDto(findRequestById(id));
    }
    public List<DetailRequestDto> getRequestsAssignedToEmployee(Employee employee){
        return requestRepository.findByAssignedTo(List.of(employee)).stream().map(requestDetailMapper::toDto).toList();
    }

    public List<DetailRequestDto> getRequestsAssignedByEmployee(Employee employee){
        return requestRepository.findByAssignedBy(employee).stream().map(requestDetailMapper::toDto).toList();
    }
    public List<DetailRequestDto> getAllRequestsDetail(){
        return requestRepository.findAll().stream().map(requestDetailMapper::toDto).toList();
    }

    public Request findRequestById(Long id){
        return requestRepository.findById(id).orElseThrow(() -> new NotFoundException("Request with id " + id + " not found"));
    }
}
