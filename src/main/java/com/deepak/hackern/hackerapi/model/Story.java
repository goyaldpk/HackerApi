package com.deepak.hackern.hackerapi.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;

@Builder
@Data
public class Story {
    @Id
    private Long id;
    private String title;
    private String Url;
    private Long score;
    private Long timeOfSubmission;
    private String user;
    @Indexed
    private Date createdTime;
    private Date updatedTime;
}
