package com.music.dto;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class FileUploadHelper {
    public final String UPLOAD_DIR = "C:\\songs";

    public boolean uploadFile(MultipartFile file, String userId) {
        boolean b = false;

        try {
            // read file
            InputStream is = file.getInputStream();
            byte data[] = new byte[is.available()];

            is.read(data);

            // write file
            FileOutputStream fos = new FileOutputStream(UPLOAD_DIR + "\\" + file.getOriginalFilename());
            fos.write(data);

            fos.flush();
            fos.close();

            b = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }
}
