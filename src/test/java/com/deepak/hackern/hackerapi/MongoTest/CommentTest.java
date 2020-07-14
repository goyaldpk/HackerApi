package com.deepak.hackern.hackerapi.MongoTest;

import com.deepak.hackern.hackerapi.model.Comment;
import com.deepak.hackern.hackerapi.repository.CommentRepo;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CommentTest {
    @Autowired
    CommentRepo commentRepo;

    Comment comment;

    @BeforeEach
    public void prepareData(){
        Comment comment = Comment.builder().commentId(1L)
                .updatedTime(new DateTime())
                .createdTime(new DateTime())
                .text("This is a Test Comment")
                .storyId(2L)
                .userAge(2)
                .userHN("testUser")
                .build();
        this.comment = comment;
    }

    @Test
    public void CommentToMongoTest() {
        Comment savedcomment = commentRepo.save(comment);
        Assert.assertEquals(comment.getStoryId(),savedcomment.getStoryId());
    }

    @After
    public void ClearDB() {
        commentRepo.delete(comment);
    }
}
