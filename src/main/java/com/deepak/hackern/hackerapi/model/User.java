package com.deepak.hackern.hackerapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class User {
    private String Id;
    private Long delay;
    private Long created;
    private Long karma;
    private String about;
    private String[] submitted;

    public User()
    {}
    public User(String id, Long delay, Long created, Long karma, String about, String[] submitted) {
        Id = id;
        this.delay = delay;
        this.created = created;
        this.karma = karma;
        this.about = about;
        this.submitted = submitted;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Long getDelay() {
        return delay;
    }

    public void setDelay(Long delay) {
        this.delay = delay;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getKarma() {
        return karma;
    }

    public void setKarma(Long karma) {
        this.karma = karma;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String[] getSubmitted() {
        return submitted;
    }

    public void setSubmitted(String[] submitted) {
        this.submitted = submitted;
    }

    @Override
    public String toString() {
        return "User{" +
                "Id='" + Id + '\'' +
                ", delay=" + delay +
                ", created=" + created +
                ", karma=" + karma +
                ", about='" + about + '\'' +
                ", submitted=" + Arrays.toString(submitted) +
                '}';
    }
}
