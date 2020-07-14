package com.deepak.hackern.hackerapi.service;

import com.deepak.hackern.hackerapi.model.Comment;
import com.deepak.hackern.hackerapi.model.Story;
import com.deepak.hackern.hackerapi.repository.CommentRepo;
import com.deepak.hackern.hackerapi.repository.StoryRepo;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class DatabaseServiceTest {

    @MockBean
    StoryRepo storyRepo;

    @MockBean
    CommentRepo commentRepo;

    @Autowired
    DataBaseService dataBaseService;

    private List<Story> stories;
    private List<Comment> comments;

    @BeforeEach
    public void prepareData() {
        Story story = Story.builder().user("testuser")
                .Url("test.com")
                .title("test")
                .id(1L)
                .score(234L)
                .timeOfSubmission(1231345L)
                .createdTime(new Date())
                .updatedTime(new Date())
                .build();
        Comment comment = Comment.builder()
                .userHN("testUser")
                .userAge(5)
                .storyId(1L)
                .text("this is a test comment")
                .commentId(1L)
                .createdTime(new DateTime())
                .updatedTime(new DateTime())
                .build();
        stories = new ArrayList<>();
        stories.add(story);
        comments = new ArrayList<>();
        comments.add(comment);
    }

    @Test
    public void getTopStoryListTest() {
        Mockito.when(storyRepo.findByOrderByCreatedTime(PageRequest.of(0,10))).thenReturn(stories);
        List<Story> mockedStories = dataBaseService.getTopStoryList();
        Assert.assertEquals(mockedStories.get(0).getId(),stories.get(0).getId());
    }

    @Test
    public void getCommentsForAStoryTest() {
        Mockito.when(commentRepo.findByStoryId(1L)).thenReturn(comments);
        List<Comment> mocketComments = dataBaseService.getCommentsForAStory(1L);
        Assert.assertEquals(mocketComments.get(0).getStoryId(),comments.get(0).getStoryId());
    }
}
