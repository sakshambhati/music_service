package com.music.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "music-service")
public class Music {

    @Id
    private String id;
    private String title;
    private String artist;
    private String userId;

    public Music(String title, String artist, String userId) {
        this.title = title;
        this.artist = artist;
        this.userId = userId;
    }
}
