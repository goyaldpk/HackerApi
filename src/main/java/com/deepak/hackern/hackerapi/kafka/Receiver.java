package com.deepak.hackern.hackerapi.kafka;

import com.deepak.hackern.hackerapi.model.Comment;
import com.deepak.hackern.hackerapi.model.KafkaProducerMessage;
import com.deepak.hackern.hackerapi.model.RawComment;
import com.deepak.hackern.hackerapi.repository.CommentRepo;
import com.deepak.hackern.hackerapi.service.HttpUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/*
* This is the kafka consumer. It recives the records and gets comments item for a particular story. Also it sorts the
* Comments according to the number of child comment that it has. Then it stores the comments in mongodb.
* */

@Slf4j
public class Receiver {

  @Autowired
  private CommentRepo commentRepo;

  @KafkaListener(topics = "${kafka.default-topic}")
  public void receive(KafkaProducerMessage kafkaProducerMessage) throws
          JsonProcessingException, ExecutionException, InterruptedException {
    log.debug("received Kafka Producer Message='{}'", kafkaProducerMessage.toString());
    if(kafkaProducerMessage!=null && kafkaProducerMessage.getKids()!=null && kafkaProducerMessage.getKids().length!=0) {
      List<CompletableFuture<String>> rawCommentFuture = HttpUtility
              .getItemsJson(Arrays.asList(kafkaProducerMessage.getKids()));
      int years = HttpUtility.getUserAge(kafkaProducerMessage.getBy());
      List<String> rawCommentStrings = rawCommentFuture.parallelStream()
              .map(CompletableFuture::join).collect(Collectors.toList());
      List<RawComment> rawComments = getTopTenCommentsForAStory(rawCommentStrings);
      List<Comment> comments = rawComments.stream().map(rawComment -> getCommentObjects(rawComment, years))
              .collect(Collectors.toList());
      commentRepo.saveAll(comments);
    }
    else {
      log.debug("there are no comments for the following story Id : "+kafkaProducerMessage.getId());
    }
  }

  private Comment getCommentObjects(RawComment rawComment, int years) {
    Comment comment = Comment.builder()
            .commentId(rawComment.getId())
            .storyId(rawComment.getParent())
            .text(rawComment.getText())
            .userHN(rawComment.getBy())
            .userAge((years))
            .createdTime(new DateTime())
            .updatedTime(new DateTime()).build();
    return comment;
  }

  private List<RawComment> getTopTenCommentsForAStory(List<String> rawCommentStrings) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    List<RawComment> rawCommentList =  new ArrayList<>();
    for(String str : rawCommentStrings) {
      RawComment rawComment = mapper.readValue(str, RawComment.class);
      if(rawComment != null && rawComment.getKids()==null) {
        rawComment.setKids(new String[0]);
        rawCommentList.add(rawComment);
      }
    }
    Collections.sort(rawCommentList);
    if(rawCommentList.size()<=10)
      return rawCommentList;
    else{
      return rawCommentList.subList(0,10);
    }
  }
}
