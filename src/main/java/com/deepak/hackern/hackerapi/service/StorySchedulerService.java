package com.deepak.hackern.hackerapi.service;

import com.deepak.hackern.hackerapi.kafka.Sender;
import com.deepak.hackern.hackerapi.model.KafkaProducerMessage;
import com.deepak.hackern.hackerapi.model.RawStory;
import com.deepak.hackern.hackerapi.model.Story;
import com.deepak.hackern.hackerapi.repository.StoryRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/*
* This class runs every 10 minutes. It fetches all the new stories and finds the top 10 out of them and stores then in
* mongo Database.
* Also it pushes the records to the kafka. So that kafka consumer can pick those records and process them for the
* comments population.
* */

@Service
@Slf4j
public class StorySchedulerService {

    @Autowired
    private Sender sender;

    @Autowired
    private StoryRepo storyRepo;

    @Scheduled(cron = "0 1-59/10 * * * ?")
    public void getTopStoriesForInterval() throws IOException {
        log.info("Scheduler started running :"+new DateTime());
        List<String> stories = HttpUtility.getTopStories();
        List<CompletableFuture<String>> rawStoryCompletableFuture = HttpUtility.getItemsJson(stories);
        if(rawStoryCompletableFuture!=null) {
            List<String> rawStoriesString = rawStoryCompletableFuture.parallelStream().map(CompletableFuture::join).collect(Collectors.toList());
            List<RawStory> rawStoryList = getSortedRawStoryObjects(rawStoriesString);

            SendStoriesToKafka(rawStoryList);

            List<Story> storiesToStore = getStoriesToSave(rawStoryList);
            List<Story> saved = storyRepo.saveAll(storiesToStore);
        }
        log.info("scheduler stopped running : "+new DateTime());
    }

    private List<Story> getStoriesToSave(List<RawStory> rawStoryList) {
        List<Story> storiesToStore = new ArrayList<>();
        int count = 0;
        for (RawStory rawStory : rawStoryList) {
            if (count == Constants.TOP_STORIES_COUNT)
                break;
            Story story = Story.builder().id(rawStory.getId())
                    .score(rawStory.getScore())
                    .title(rawStory.getTitle())
                    .Url(rawStory.getUrl())
                    .timeOfSubmission(rawStory.getTime())
                    .user(rawStory.getBy())
                    .createdTime(new Date())
                    .updatedTime(new Date())
                    .build();
            storiesToStore.add(story);
            count++;
        }
        return storiesToStore;
    }

    private void SendStoriesToKafka(List<RawStory> rawStoryList) {
        for (RawStory rawStory : rawStoryList) {
            KafkaProducerMessage kafkaProducerMessage = new KafkaProducerMessage()
                    .setBy(rawStory.getBy())
                    .setId(rawStory.getId())
                    .setKids(rawStory.getKids());
            sender.send(kafkaProducerMessage);
        }
    }

    private List<RawStory> getSortedRawStoryObjects(List<String> rawStories) {
        ObjectMapper mapper = new ObjectMapper();
        List<RawStory> rawStoryList = new ArrayList<>();
        for (String str : rawStories) {
            try {
                RawStory rawStory = mapper.readValue(str, RawStory.class);
                if (rawStory != null){
                    if (rawStory.getScore() == null)
                        rawStory.setScore(0L);
                    rawStoryList.add(rawStory);
                }
            }catch (JsonProcessingException ex){
                log.error("exception occured while reading msg.");
            }
        }
        Collections.sort(rawStoryList);
        return rawStoryList;
    }

}
