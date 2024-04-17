package cz.fel.cvut.behelpdesk.service;

import cz.fel.cvut.behelpdesk.dao.EmailRequest;
import cz.fel.cvut.behelpdesk.dao.Employee;
import cz.fel.cvut.behelpdesk.dto.DetailEmployeeDto;
import cz.fel.cvut.behelpdesk.dto.DetailRequestDto;
import cz.fel.cvut.behelpdesk.dto.InputRequestDto;
import cz.fel.cvut.behelpdesk.dao.Request;
import cz.fel.cvut.behelpdesk.dto.RequestDto;
import cz.fel.cvut.behelpdesk.enumeration.CategoryEnum;
import cz.fel.cvut.behelpdesk.enumeration.StateEnum;
import cz.fel.cvut.behelpdesk.exception.NotFoundException;
import cz.fel.cvut.behelpdesk.mapper.RequestDetailMapper;
import cz.fel.cvut.behelpdesk.mapper.RequestMapper;
import cz.fel.cvut.behelpdesk.repository.RequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;
    private final RequestDetailMapper requestDetailMapper;
    private final EmailService emailService;
    private final EmployeeService employeeService;

    @Transactional
    public RequestDto createRequest(InputRequestDto inputRequestDto){
        Request requestToSave = requestMapper.toEntity(inputRequestDto);
        Request savedRequest = requestRepository.save(requestToSave);
        RequestDto savedRequestDto = requestMapper.toDto(savedRequest);

        EmailRequest emailRequest = new EmailRequest();
        List<DetailEmployeeDto> employees = employeeService.getEmployeesByCategory(savedRequest.getRequestCategory());
        Set<String> recipients = employees.stream()
                .map(employee -> employee.username() + "@ulz.cz")
                .collect(Collectors.toSet());
        emailRequest.setSubject("Na Helpdesku byl nahlášen požadavek s názvem: " + savedRequest.getName());
        emailRequest.setMessage(
                "Uživatel: "+ "<b>"+savedRequest.getAssignedBy().getForename() + " " + savedRequest.getAssignedBy().getSurname() + "</b>"+"<br>"
                + "Nahlásil požadavek: " + "<b>"+ savedRequest.getName() + "</b>" +  "<br>"
                + "S prioritou: "  + "<b>"+ savedRequest.getRequestPriority().getCzechString() + "</b>" +   "<br>"
                + "V kategorii: "  + "<b>"+ savedRequest.getRequestCategory().getCzechString() + "</b>" +   "<br>"
                + "Popis požadavku: "
                + "<br>"
                +  savedRequest.getDescription() +   "<br>"
                + "<br>"
                + "Prosíme o převzetí tohoto požadavku: " +  "<a href=\"http://192.168.10.31:3000/requests/"+savedRequest.getId()+"\" target=\"_blank\">Odkaz na požadavek</a>"
                + "<br>"
                + "<br>"
                + "Automatická zpráva - tato zpráva je automaticky generovaná, neodpovídejte na ni prosím.");

        emailRequest.setRecipients(recipients);
        emailService.sendEmail(emailRequest);

        return savedRequestDto;
    }

    public Map<StateEnum, Long> countRequestsByState() {
        return requestRepository.findAll().stream()
                .collect(Collectors.groupingBy(Request::getRequestState, Collectors.counting()));
    }

    public Map<CategoryEnum, Long> countRequestsByCategory() {
        return requestRepository.findAll().stream()
                .collect(Collectors.groupingBy(Request::getRequestCategory, Collectors.counting()));
    }
    public Map<LocalDate, Long> countRequestsByDate() {
    LocalDateTime ninetyDaysAgo = LocalDateTime.now().minusDays(90);
    List<Request> requests = requestRepository.findAllOpenedFrom(ninetyDaysAgo);
    return requests.stream()
        .collect(Collectors.groupingBy(request -> request.getDateOfAnnouncement().toLocalDate(), Collectors.counting()));
}


    @Transactional
    public RequestDto updateRequest(Long id, InputRequestDto inputRequestDto){
        Request requestToUpdate = findRequestById(id);
        if(requestToUpdate.getRequestState() != StateEnum.SOLVED && inputRequestDto.requestState() == StateEnum.SOLVED
        && requestToUpdate.getRequestCategory() == inputRequestDto.requestCategory()
        && requestToUpdate.getRequestPriority() == inputRequestDto.requestPriority()){
            requestToUpdate.setDateOfCompletion(LocalDateTime.now());
            EmailRequest emailRequest = new EmailRequest();
            Set<String> recipients = Set.of(requestToUpdate.getAssignedBy().getUsername() + "@ulz.cz");
            emailRequest.setSubject("Váš požadavek s názvem: " + requestToUpdate.getName()+" byl vyřešen");
            emailRequest.setMessage(
                    "Dobrý den,"
                    +   "<br>"
                    + "<br>" + "Váš požadavek s názvem: "+ "<b>"+requestToUpdate.getName() +"</b>"+ " byl vyřešen." + "<br>"
                    +  "<a href=\"http://192.168.10.31:3000/requests/"+requestToUpdate.getId()+"\" target=\"_blank\">Odkaz na požadavek</a>" + "<br>"
                    +   "<br>"
                    + "S pozdravem,"
                    +   "<br>"
                    + "Helpdesk ÚLZ"
                    +   "<br>"
                    +   "<br>"
                    + "Automatická zpráva - tato zpráva je automaticky generovaná, neodpovídejte na ni prosím.");

            emailRequest.setRecipients(recipients);
            emailService.sendEmail(emailRequest);
        }
        else {
            requestToUpdate.setDateOfCompletion(null);
        }

        if (inputRequestDto.requestState() == StateEnum.IN_PROGRESS && requestToUpdate.getAssignedTo().isEmpty() && !inputRequestDto.assignedTo().isEmpty()) {
            EmailRequest emailRequest = new EmailRequest();
            Set<String> recipients = Set.of(requestToUpdate.getAssignedBy().getUsername() + "@ulz.cz");
            emailRequest.setSubject("Váš požavek s názvem: " + requestToUpdate.getName()+" je nyní v řešení");
            emailRequest.setMessage(
                    "Dobrý den,"
                            +   "<br>"
                            + "<br>" + "Váš požavek s názvem: "+ "<b>"+requestToUpdate.getName() +"</b>"+ " je nyní v řešení a byl k němu přiřazen řešitel." + "<br>"
                            +  "<a href=\"http://192.168.10.31:3000/requests/"+requestToUpdate.getId()+"\" target=\"_blank\">Odkaz na požadavek</a>" + "<br>"
                            +   "<br>"
                            + "S pozdravem,"
                            +   "<br>"
                            + "Helpdesk ÚLZ"
                            +   "<br>"
                            +   "<br>"
                            + "Automatická zpráva - tato zpráva je automaticky generovaná, neodpovídejte na ni prosím.");

            emailRequest.setRecipients(recipients);
            emailService.sendEmail(emailRequest);
        }
        else if(requestToUpdate.getAssignedTo().isEmpty() && !inputRequestDto.assignedTo().isEmpty()){
            EmailRequest emailRequest = new EmailRequest();
            Set<String> recipients = Set.of(requestToUpdate.getAssignedBy().getUsername() + "@ulz.cz");
            emailRequest.setSubject("K Vašemu požadavku s názvem: " + requestToUpdate.getName()+" byl přiřazen řešitel");
            emailRequest.setMessage(
                    "Dobrý den,"
                            +   "<br>"
                            + "<br>" + "k Vašemu požadavku s názvem: "+ "<b>"+requestToUpdate.getName() +"</b>"+ " byl přiřazen řešitel." + "<br>"
                            +  "<a href=\"http://192.168.10.31:3000/requests/"+requestToUpdate.getId()+"\" target=\"_blank\">Odkaz na požadavek</a>" + "<br>"
                            +   "<br>"
                            + "S pozdravem,"
                            +   "<br>"
                            + "Helpdesk ÚLZ"
                            +   "<br>"
                            +   "<br>"
                            + "Automatická zpráva - tato zpráva je automaticky generovaná, neodpovídejte na ni prosím.");

            emailRequest.setRecipients(recipients);
            emailService.sendEmail(emailRequest);
        }
        else  if (inputRequestDto.requestState() == StateEnum.INVALID) {
            EmailRequest emailRequest = new EmailRequest();
            Set<String> recipients = Set.of(requestToUpdate.getAssignedBy().getUsername() + "@ulz.cz");
            emailRequest.setSubject("Váš požavek s názvem: " + requestToUpdate.getName()+" byl označen jako neplatný.");
            emailRequest.setMessage(
                    "Dobrý den,"
                            +   "<br>"
                            + "<br>" + "Váš požavek s názvem: "+ "<b>"+requestToUpdate.getName() +"</b>"+ " byl označen jako neplatný." + "<br>"
                            +  "<a href=\"http://192.168.10.31:3000/requests/"+requestToUpdate.getId()+"\" target=\"_blank\">Odkaz na požadavek</a>" + "<br>"
                            +   "<br>"
                            + "S pozdravem,"
                            +   "<br>"
                            + "Helpdesk ÚLZ"
                            +   "<br>"
                            +   "<br>"
                            + "Automatická zpráva - tato zpráva je automaticky generovaná, neodpovídejte na ni prosím.");

            emailRequest.setRecipients(recipients);
            emailService.sendEmail(emailRequest);
        }


        requestToUpdate = requestMapper.partialUpdate(inputRequestDto, requestToUpdate);
        return requestMapper.toDto(requestRepository.save(requestToUpdate));
    }

    public List<DetailRequestDto> getRequestsByCategory(CategoryEnum category){
        return requestRepository.findByRequestCategory(category).stream().map(requestDetailMapper::toDto).toList();
    }
    public List<DetailRequestDto> getRequestsByCategories(List<CategoryEnum> categories){
        return requestRepository.findByRequestCategoryIn(categories).stream().map(requestDetailMapper::toDto).toList();
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
