package com.deepak.hackern.hackerapi.service;

import com.deepak.hackern.hackerapi.model.Comment;
import com.deepak.hackern.hackerapi.model.Story;
import com.deepak.hackern.hackerapi.repository.CommentRepo;
import com.deepak.hackern.hackerapi.repository.StoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataBaseService {

    @Autowired
    StoryRepo storyRepo;

    @Autowired
    CommentRepo commentRepo;

    public List<Story> getTopStoryList() {
        return storyRepo.findByOrderByCreatedTime(PageRequest.of(0,10));
    }

    public List<Comment> getCommentsForAStory(Long storyId) {
        return commentRepo.findByStoryId(storyId);
    }

    public List<Story> getAllPastStories() {
        return storyRepo.findAll();
    }
}
