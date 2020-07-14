package com.deepak.hackern.hackerapi.controller;

import com.deepak.hackern.hackerapi.model.Comment;
import com.deepak.hackern.hackerapi.model.Story;
import com.deepak.hackern.hackerapi.service.DataBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@ComponentScan
public class HackerApiController {

    @Autowired
    DataBaseService dataBaseService;

    @GetMapping(value="/top-stories", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Story> getTopStories() {
        return dataBaseService.getTopStoryList();
    }

    @GetMapping(value = "/comments/{storyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Comment> getStoryComments(@PathVariable String storyId) {
        return dataBaseService.getCommentsForAStory(Long.parseLong(storyId));
    }

    @GetMapping(value = "/past-stories", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Story> getPastStories() {
        return dataBaseService.getAllPastStories();
    }
}
