package com.music.repo;

import com.music.entity.Music;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicRepo extends MongoRepository<Music, String> {

    Music findByUserId(String userId);

//    List<Music> saveAll();
}
