package com.deepak.hackern.hackerapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

public class RawComment implements Comparable<RawComment>{
    private String by;
    private Long id;
    private String[] kids;
    private Long parent;
    private String text;
    private Long time;
    private String type;
    @JsonIgnore
    private String error;
    @JsonIgnore
    private boolean deleted;
    @JsonIgnore
    private boolean dead;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public RawComment() {
    }

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

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public int compareTo(RawComment rawComment) {
        if(this.kids.length > rawComment.kids.length)
            return -1;
        if(this.kids.length < rawComment.kids.length)
            return 1;
        return 0;
    }
}
