package com.music.service;

import com.music.entity.Music;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MusicService {

     List<Music> getAllMusic();

     Music getMusic(String userId);

     Object addMusic(String mId, String title, String artist, String userId);

     String storeFile(MultipartFile file);

     Resource downloadFile(String fileName);


     //    public List<Music> getMusicOfUser(Long userId);

      Music addMusic(Music music);

     // List<Music> findByUserId(String userId);

     Music downloadSong(String id);
}