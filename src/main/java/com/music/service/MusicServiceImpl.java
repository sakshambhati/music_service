package com.music.service;

import com.music.entity.Music;
//import com.music.repo.MusicRepo;
import com.music.repo.MusicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class MusicServiceImpl implements MusicService {

    @Autowired
    private MusicRepo musicRepo;

    private Path fileStoragePath;
    private String fileStorageLocation;


    // get all music ---->   admin
    public List<Music> getAllMusic() {
        return musicRepo.findAll();
    }

    public Music addMusic(Music music) {
        return musicRepo.save(music);
    }


    public Music getMusic(String userId) {
        Music m = musicRepo.findByUserId(userId);
        return m;
    }

    @Override
    public Music addMusic(String mId, String title, String artist, String userId) {

        Music m = new Music(title, artist, userId);
        // File file = new File("C:\\songs\\" + mId );
        return musicRepo.save(m);
    }


    @Override
    public Music downloadSong(String id) {
        return musicRepo.findById(id).orElse(null);
    }










    // green learner

    public MusicServiceImpl(@Value("${file.storage.location:temp}") String fileStorageLocation) {
        this.fileStorageLocation = fileStorageLocation;
        this.fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
        try {
            Files.createDirectories(fileStoragePath);
        } catch (IOException e) {
            throw new RuntimeException("Not created");
        }
    }
    @Override
    public String storeFile(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        Path filepath = Paths.get(fileStoragePath + "\\" + filename);
        try {
            Files.copy(file.getInputStream(), filepath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Error in storing file");
        }
        return filename;
    }

    @Override
    public Resource downloadFile(String fileName) {
        Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(fileName);

        Resource resource;
        try {
            resource = new UrlResource(path.toUri());

        } catch (MalformedURLException e) {
            throw new RuntimeException("Issue in reading the file", e);
        }

        //
        //if(resource.exists() && resource.isReadable()){
            return resource;
       // }else{
         //   throw new RuntimeException("the file doesn't exist or not readable");
        //}
    }
}


    //    @Override
//    public List<Music> findByUserId(String userId) {
//        return musicRepo.findByUserId(userId);
//    }


    //    // data
//    List<Music> list = List.of(
//            new Music("1", "Jazz", "Juke", "12"),
//            new Music("2", "Pop", "Vedi", "13"),
//            new Music("3", "Beats", "M&M", "14"),
//            new Music("4", "Rap", "Sam", "14"),
//            new Music("5", "Classic", "MGK", "13"),
//            new Music("6", "Rock", "Tata", "12")
//    );


//    @Override
//    public List<Music> getMusicOfUser(Long userId) {
//        return list.stream().filter(music -> music.getUserId().equals(userId)).collect(Collectors.toList());  // list
//    }

