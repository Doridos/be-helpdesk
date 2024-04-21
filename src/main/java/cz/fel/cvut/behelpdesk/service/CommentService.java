package cz.fel.cvut.behelpdesk.service;

import cz.fel.cvut.behelpdesk.dao.Comment;
import cz.fel.cvut.behelpdesk.dao.EmailRequest;
import cz.fel.cvut.behelpdesk.dao.Employee;
import cz.fel.cvut.behelpdesk.dao.Request;
import cz.fel.cvut.behelpdesk.dto.CommentDto;
import cz.fel.cvut.behelpdesk.dto.InputCommentDto;
import cz.fel.cvut.behelpdesk.dto.InputRequestDto;
import cz.fel.cvut.behelpdesk.dto.RequestDto;
import cz.fel.cvut.behelpdesk.mapper.CommentMapper;
import cz.fel.cvut.behelpdesk.mapper.RequestMapper;
import cz.fel.cvut.behelpdesk.repository.CommentRepository;
import cz.fel.cvut.behelpdesk.repository.EmployeeRepository;
import cz.fel.cvut.behelpdesk.repository.RequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final RequestRepository requestRepository;
    private final EmployeeRepository employeeRepository;
    private final CommentMapper commentMapper;
    private final EmailService emailService;

    @Transactional
    public CommentDto createComment(InputCommentDto inputCommentDto){
        Comment commentToSave = commentMapper.toEntity(inputCommentDto);
        commentToSave.setDateOfComment(LocalDateTime.now(ZoneId.of("CET")));
        CommentDto savedCommentDto = commentMapper.toDto(commentRepository.save(commentToSave));
        Request commentedRequest = requestRepository.findById(inputCommentDto.requestId())
                .orElseThrow(() -> new IllegalArgumentException("Request with id: " + inputCommentDto.requestId() + " not found"));
        Employee authorOfComment = employeeRepository.findById(inputCommentDto.authorUsername())
                .orElseThrow(() -> new IllegalArgumentException("Employee with username: " + inputCommentDto.authorUsername() + " not found"));
        if(commentedRequest.getComments().isEmpty() && !commentedRequest.getAssignedBy().getUsername().equals(inputCommentDto.authorUsername())){
            EmailRequest emailRequest = new EmailRequest();
            Set<String> recipients = Set.of(commentedRequest.getAssignedBy().getUsername() + "@ulz.cz");
            emailRequest.setSubject("K Vašemu požadavku s názvem: " + commentedRequest.getName()+" byl přidán první komentář");
            emailRequest.setMessage(
                    "Dobrý den,"
                            +   "<br>"
                            + "<br>" + "k Vašemu požadavku s názvem: "+ "<b>"+commentedRequest.getName() +"</b>"+ " byl přidán první komentář:" + "<br>"
                            + "<br>"
                            + "<b>"+ authorOfComment.getForename() + " " + authorOfComment.getSurname() +"</b>"+ " napsal/a: " + "<br>"
                            + commentToSave.getContent()
                            + "<br>"
                            + "<br>"
                            +  "<a href=\"http://helpdesk/requests/"+commentedRequest.getId()+"\" target=\"_blank\">Odkaz na požadavek</a>" + "<br>"
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

        return savedCommentDto;
    }
}
