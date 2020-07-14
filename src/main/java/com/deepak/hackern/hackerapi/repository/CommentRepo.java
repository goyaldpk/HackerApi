package com.deepak.hackern.hackerapi.repository;

import com.deepak.hackern.hackerapi.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends MongoRepository<Comment, String> {
    List<Comment> findByStoryId(Long storyId);
}
