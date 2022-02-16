package com.jira.integration.demo.Service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jira.integration.demo.Model.JiraPayload;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Service
public class JiraTicketService {

    private RestTemplate restTemplate;
    @Value("${jira.username}")
    private String username;
    @Value("${jira.secret}")
    private String password;
    @Value("${jira.baseURL}")
    private String baseURL;
    @Value("${jira.createticketURL}")
    private String createticketURL;

    @Autowired
    public JiraTicketService(RestTemplate restTemplate){
        this.restTemplate= restTemplate;
    }

    public String createJiraTicket(JiraPayload jiraPayload){
        System.out.println(baseURL.concat(createticketURL));
        ResponseEntity<String> response= this.restTemplate.exchange(baseURL.concat(createticketURL), HttpMethod.POST,
                new HttpEntity<JiraPayload>(jiraPayload,getHeader()),String.class);
        if(response != null){
            return response.getBody();
        }
        return null;
    }

    public HttpHeaders getHeader(){
        String auth = username + ":" + password;
        byte[] encodeauth= Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader= "Basic "+new String(encodeauth);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization",authHeader);
        return httpHeaders;
    }
}
