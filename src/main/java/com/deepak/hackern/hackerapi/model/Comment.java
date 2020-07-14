package com.deepak.hackern.hackerapi.model;

import lombok.Builder;
import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;

@Builder
@Data
public class Comment {
    private Long commentId;
    private Long storyId;
    private String text;
    private String userHN;
    private int userAge;
    private DateTime createdTime;
    private DateTime updatedTime;
}
