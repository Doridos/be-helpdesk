package cz.fel.cvut.behelpdesk.dao;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class EmailRequest {

    String subject;
    String message;
    Set<String> recipients;
}