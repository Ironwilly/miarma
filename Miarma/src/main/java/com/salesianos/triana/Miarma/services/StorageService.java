


package com.salesianos.triana.Miarma.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {


    void init();

    String store(MultipartFile file);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    BufferedImage simpleResizer(BufferedImage bufferedImage, int width);

    void deleteFile(String filename);

    void deleteAll();

    boolean resizeImage(File sourceFile);

}



