package com.deepak.hackern.hackerapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Arrays;


public class RawStory implements Comparable<RawStory> {
    private String by;
    private Long descendants;
    private Long id;
    private String[] kids;
    private Long score;
    private Long time;
    private String title;
    private String type;
    private String url;
    private String parent;
    private String text;
    @JsonIgnore
    private String error;

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String[] getKids() {
        return kids;
    }

    public void setKids(String[] kids) {
        this.kids = kids;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getDescendants() {
        return descendants;
    }

    public void setDescendants(Long descendants) {
        this.descendants = descendants;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "RawStory{" +
                "by='" + by + '\'' +
                ", id=" + id +
                ", kids=" + Arrays.toString(kids) +
                ", score=" + score +
                ", time=" + time +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public int compareTo(RawStory rawStory) {
        if(this.score > rawStory.score)
            return -1;
        if(this.score < rawStory.score)
            return 1;
        return 0;
    }
}
