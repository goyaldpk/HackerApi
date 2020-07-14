package com.deepak.hackern.hackerapi.service;

import com.deepak.hackern.hackerapi.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.Instant;
import org.joda.time.Period;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/*
* This is the utility class for HTTP request. It uses Java 11 async Http Api.
* */

@Component
@Slf4j
public class HttpUtility {

    private static HttpClient client = HttpClient.newHttpClient();

    public static List<String> getTopStories() {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(Constants.NEW_STORIES_URL))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            log.error("IO exception occurred while getting new Stories");
            e.printStackTrace();
        } catch (InterruptedException e) {
            log.error("Interrupted exception occurred while getting new Stories");
            e.printStackTrace();
        }
        List<String> storyIds = new ArrayList<>();
        if(response.body()!=null) {
            String[] tokenizedResponse = response.body().substring(1, response.body().length() - 1).split(",");
            for (String str : tokenizedResponse)
                storyIds.add(str);
            log.debug("Number of story id returned" + storyIds.size());
        }
        return storyIds;
    }

    public static CompletableFuture<HttpResponse<String>> getItem(String uri) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }

    public static List<CompletableFuture<String>> getItemsJson(List<String> stories) {
        if(stories!=null && stories.size()!=0)
        return stories.parallelStream()
                .map(storyId -> HttpUtility.getItem(Constants.ITEM_URL + storyId + ".json")
                        .thenApplyAsync(HttpResponse::body))
                .collect(Collectors.toList());
        return null;
    }

    public static int getUserAge(String by) throws JsonProcessingException, ExecutionException, InterruptedException {
        if(by!=null && !by.isEmpty()) {
            CompletableFuture<HttpResponse<String>> userString = getItem(Constants.USER_URL + by + ".json");
            ObjectMapper objectMapper = new ObjectMapper();
            User user = objectMapper.readValue(userString.get().body(), User.class);
            Period period = new Period(Instant.ofEpochSecond(user.getCreated()), Instant.now());
            log.info("the age os the user is"+period.getYears());
            return period.getYears();
        }
        return -1;
    }
}
