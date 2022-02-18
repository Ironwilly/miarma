
package com.salesianos.triana.Miarma.controller;
import com.salesianos.triana.Miarma.dto.FileResponse;
import com.salesianos.triana.Miarma.services.StorageService;
import com.salesianos.triana.Miarma.utils.MediaTypeUrlResource;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;




@RestController
@RequiredArgsConstructor
public class FileController {


    private final StorageService storageService;


    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestPart("file") MultipartFile file) throws IOException {


        String name = storageService.store(file);
        String extension = StringUtils.getFilenameExtension(name);
        BufferedImage originalImage = ImageIO.read(file.getInputStream());
        BufferedImage escaledImage = storageService.simpleResizer(originalImage, 128);
        OutputStream outputStream = Files.newOutputStream(storageService.load(name));
        ImageIO.write(escaledImage,extension,outputStream);



        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(name)
                .toUriString();

        FileResponse response = FileResponse.builder()
                .name(name)
                .size(file.getSize())
                .type(file.getContentType())
                .uri(uri)
                .build();

        return ResponseEntity.created(URI.create(uri)).body(response);

    }
/*
    @PostMapping("/upload2")
    @ResponseBody
    public List<ResponseEntity<?>> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.stream(files)
                .map(file -> upload(file))
                .collect(Collectors.toList());
    }

 */


    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        MediaTypeUrlResource resource = (MediaTypeUrlResource) storageService.loadAsResource(filename);


        return ResponseEntity.status(HttpStatus.OK)
                .header("content-type", resource.getType())
                .body(resource);


    }
}
