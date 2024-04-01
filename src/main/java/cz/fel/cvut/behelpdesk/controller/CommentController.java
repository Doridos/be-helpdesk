package cz.fel.cvut.behelpdesk.controller;

import cz.fel.cvut.behelpdesk.dto.CommentDto;
import cz.fel.cvut.behelpdesk.dto.InputCommentDto;
import cz.fel.cvut.behelpdesk.dto.InputRequestDto;
import cz.fel.cvut.behelpdesk.dto.RequestDto;
import cz.fel.cvut.behelpdesk.service.CommentService;
import cz.fel.cvut.behelpdesk.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentDto> addComment(@RequestBody InputCommentDto inputCommentDto) {
        return new ResponseEntity<>(commentService.createComment(inputCommentDto), HttpStatus.OK);
    }
}
