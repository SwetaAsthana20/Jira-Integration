package com.jira.integration.demo.Model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "project",
        "summary",
        "description",
        "issuetype"
})
@Data
@Builder
public class Fields {

    @JsonProperty("project")
    private Project project;
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("description")
    private String description;
    @JsonProperty("issuetype")
    private Issuetype issuetype;

}