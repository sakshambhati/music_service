package com.music.controller;

import com.music.dto.FileUploadHelper;
import com.music.dto.FileUploadResponse;
import com.music.entity.Music;
import com.music.repo.MusicRepo;
import com.music.service.MusicService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/music")
public class MusicController {

    Logger logger = LoggerFactory.getLogger(MusicController.class);

    @Autowired
    private MusicService musicService;

    @Autowired
    MusicRepo musicRepo;

    @Autowired
    private FileUploadHelper fileUploadHelper;

//    @GetMapping("/user/{userId}")
//    @RolesAllowed("user")
//    public List<Music> getMusic(@PathVariable Long userId) {
//        return musicService.getMusicOfUser(userId);
//    }


    @PostMapping("/addSong")
    public String saveMusic(@RequestParam("title") String title, @RequestParam("artist") String artist,
                             @RequestParam("file") MultipartFile file, Principal principal) {
        try {
            logger.info("Entering saveMusic");
            String userId = principal.getName();

            Music m = new Music(title, artist, userId);
            musicService.addMusic(m);
            String objectId = m.getId();
            String path = "C:\\songs";
//            // check
//            String[] f = file.getOriginalFilename().split("\\.");
//            String extension = f[f.length-1];
//
//            Music m = new Music(mId, title, artist, /*userId mp3*/);
//
//            musicRepo.save(m);

            byte[] bytes = file.getBytes();
            Path path1 = Paths.get(path, objectId);
            Files.write(path1, bytes);

//            String filename = String.valueOf(musicService.addMusic(m));
//          return new ResponseEntity<>(musicService.addMusic(mId, title, artist, userId), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("Problem in adding song");
        }
        return "File saved successfully";
    }


    @GetMapping("/download/{name}")
    public ResponseEntity<Resource> downloadSong(@PathVariable String name) {
        logger.info("Entering download");

        Music m = musicService.downloadSong(name);
        String title = m.getTitle();
        Resource resource;
        Path path = Paths.get("C:\\songs\\" + name);
        try {
            resource = new UrlResource(path.toUri());
        } catch (IOException e) {
            throw new RuntimeException("Not downloaded");
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + title + ".mp3" + "\"" )
                .body(resource);
    }





    @GetMapping("/getSong/{userId}")
    public ResponseEntity<Music> getMusicForUser(@PathVariable String userId) {
        try {
            logger.info("Entering getMusicForUser");
            return new ResponseEntity<>(musicService.getMusic(userId), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("Problem in getting song");
        }
    }

    // admin
    @GetMapping("/all")
    @RolesAllowed("admin")
    public ResponseEntity<List<Music>> getAllMusic() {
        try {
            logger.info("Entering getAllMusic");
            return new ResponseEntity<>(musicService.getAllMusic(), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("Problem in getting song");
        }
    }


//    // d
//    @PostMapping("/{userId}/upload-file")
//    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable String userId,
//                                        @RequestParam("title") String title, @RequestParam("artist") String artist) {
//
//        try {
//            if (file.isEmpty()) {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Request must contain file");
//            }
//
//            // file upload
//            Music m = new Music(userId, title, artist, userId);
//            boolean b = fileUploadHelper.uploadFile(file, userId);
//            if (b) {
//                return ResponseEntity.ok("File uploaded successfully");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("OOPS!");
//    }
//
//
//
//
//
//
//
//
//
//
//    // constructor
//    public MusicController(MusicService musicService) {
//        this.musicService = musicService;
//    }
//
//    // green learner
//    @PostMapping("/upload")
//    public FileUploadResponse SingleFileUpload(@RequestParam("file") MultipartFile file) {
//
//    String fileName = musicService.storeFile(file);
//
//    String url = ServletUriComponentsBuilder.fromCurrentContextPath()
//            .path("/download/")  //
//            .path(fileName)
//            .toUriString();
//
//    String contentType = file.getContentType();
//
//    FileUploadResponse response = new FileUploadResponse(fileName, contentType, url);
//
//        return response;
//
//    }
//
//

//    @GetMapping("/download/{fileName}")
//    ResponseEntity<Resource> downLoadSingleFile(@PathVariable String fileName, HttpServletRequest request) {
//
//        Resource resource = musicService.downloadFile(fileName);
//
//      //  MediaType contentType = MediaType.APPLICATION_OCTET_STREAM;
//
//        String mimeType;
//
//        try {
//            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//        } catch (IOException e) {
//            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
//        }
//       // mimeType = mimeType == null ? MediaType.APPLICATION_OCTET_STREAM_VALUE : mimeType;  //
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(mimeType))
//    //            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName="+resource.getFilename())
//                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename())
//                .body(resource);
//    }


}