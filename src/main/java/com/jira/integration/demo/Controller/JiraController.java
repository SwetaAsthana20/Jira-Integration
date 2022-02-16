package com.jira.integration.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.jira.integration.demo.Service.JiraTicketService;
import com.jira.integration.demo.Model.JiraPayload;

@RestController
public class JiraController {

    private JiraTicketService jiraTicketService;


    @Autowired
    public JiraController(JiraTicketService jiraTicketService){
        this.jiraTicketService = jiraTicketService;
    }

    @PostMapping("/ticket.create")
    public ResponseEntity<String> createTicket(@RequestBody JiraPayload jiraPayload){
        String response = jiraTicketService.createJiraTicket(jiraPayload);
        return new ResponseEntity<String>(response, HttpStatus.CREATED);
    }

}
