package com.deepak.hackern.hackerapi.model;

import java.util.Arrays;

public class KafkaProducerMessage {
    private Long id;
    private String[] kids;
    private String by;

    public Long getId() {
        return id;
    }

    public KafkaProducerMessage setId(Long id) {
        this.id = id;
        return this;
    }

    public String[] getKids() {
        return kids;
    }

    public KafkaProducerMessage setKids(String[] kids) {
        this.kids = kids;
        return this;
    }

    public String getBy() {
        return by;
    }

    public KafkaProducerMessage setBy(String by) {
        this.by = by;
        return this;
    }

    @Override
    public String toString() {
        return "KafkaProducerMessage{" +
                "id=" + id +
                ", kids=" + Arrays.toString(kids) +
                ", by='" + by + '\'' +
                '}';
    }
}
