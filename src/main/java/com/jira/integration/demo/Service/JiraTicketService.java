package com.jira.integration.demo.Service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jira.integration.demo.Model.JiraPayload;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

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
    @Value("${jira.addAttachmentURL}")
    private String addAttachmentURL;

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

    public String addAttachments(String jiraID, Collection<String> content, String fileName) throws IOException {
        System.out.println(baseURL.concat(addAttachmentURL));
        String endpoint = String.format(baseURL+addAttachmentURL, jiraID) ;

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        File tempFile = File.createTempFile(fileName, ".txt");

        BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
        for(String entry: content){
            bw.write(entry+"\n");
        }
        bw.close();

        FileSystemResource fileSystemResource = new FileSystemResource(tempFile);
        body.add("file",fileSystemResource);

        HttpHeaders headers = getHeader();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add("X-Atlassian-Token", "no-check");

        HttpEntity<MultiValueMap<String, Object>> request= new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(endpoint, request, String.class);

        System.out.println(response);
        return response.getBody();
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
