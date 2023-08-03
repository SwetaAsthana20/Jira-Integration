package com.jira.integration.demo.Controller;

import com.jira.integration.demo.Model.Fields;
import com.jira.integration.demo.common.constants.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.jira.integration.demo.Service.JiraTicketService;
import com.jira.integration.demo.Model.JiraPayload;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@RestController
public class JiraController {

    private JiraTicketService jiraTicketService;


    @Autowired
    public JiraController(JiraTicketService jiraTicketService){
        this.jiraTicketService = jiraTicketService;
    }

    @PostMapping("/ticket.create")
    public ResponseEntity<String> createTicket(@RequestBody JiraPayload jiraPayload){
        Fields field = jiraPayload.getFields();
        field.setDescription(Description.REPORT_1);
        jiraPayload.setFields(field);
        String response = jiraTicketService.createJiraTicket(jiraPayload);
        return new ResponseEntity<String>(response, HttpStatus.CREATED);
    }

    @PostMapping("/ticket.upload")
    public ResponseEntity<String> addAttachment(@RequestBody String jiraID) throws IOException {
        Collection<String> data = new ArrayList<>();
        data.add("asd");
        data.add("sdf");
        String response = jiraTicketService.addAttachments(jiraID, data, "xyz");
        return new ResponseEntity<String>(response, HttpStatus.CREATED);
    }
}
