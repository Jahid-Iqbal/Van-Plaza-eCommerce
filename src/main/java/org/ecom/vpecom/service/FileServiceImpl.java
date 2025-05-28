package org.ecom.vpecom.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile image) throws IOException {
        String originalFileName = image.getOriginalFilename();
        String randonUUID = UUID.randomUUID().toString();
        String newFileName = randonUUID.concat(originalFileName.substring(originalFileName.lastIndexOf(".")));
        String filePath = path + File.separator + newFileName;

        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }

        Files.copy(image.getInputStream(), Paths.get(filePath));
        return newFileName;

    }
}
