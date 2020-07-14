package com.deepak.hackern.hackerapi.repository;

import com.deepak.hackern.hackerapi.model.Story;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface StoryRepo extends MongoRepository<Story, String> {
    List<Story> findByOrderByCreatedTime(Pageable pageable);
}
