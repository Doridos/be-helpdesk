package cz.fel.cvut.behelpdesk.service;

import cz.fel.cvut.behelpdesk.dao.Comment;
import cz.fel.cvut.behelpdesk.dao.Request;
import cz.fel.cvut.behelpdesk.dto.CommentDto;
import cz.fel.cvut.behelpdesk.dto.InputCommentDto;
import cz.fel.cvut.behelpdesk.dto.InputRequestDto;
import cz.fel.cvut.behelpdesk.dto.RequestDto;
import cz.fel.cvut.behelpdesk.mapper.CommentMapper;
import cz.fel.cvut.behelpdesk.mapper.RequestMapper;
import cz.fel.cvut.behelpdesk.repository.CommentRepository;
import cz.fel.cvut.behelpdesk.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;
    public CommentDto createComment(InputCommentDto inputCommentDto){
        Comment requestToSave = commentMapper.toEntity(inputCommentDto);
        return commentMapper.toDto(commentRepository.save(requestToSave));
    }
}
