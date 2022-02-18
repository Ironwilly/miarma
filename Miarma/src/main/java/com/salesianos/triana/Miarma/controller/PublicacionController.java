package com.salesianos.triana.Miarma.controller;


import com.salesianos.triana.Miarma.Repositorios.PublicacionRepository;
import com.salesianos.triana.Miarma.dto.CreatePublicacionDto;
import com.salesianos.triana.Miarma.models.Publicacion;
import com.salesianos.triana.Miarma.services.impl.PublicacionServiceImpl;
import com.salesianos.triana.Miarma.services.StorageService;
import com.salesianos.triana.Miarma.users.dto.CreateUserDto;
import com.salesianos.triana.Miarma.users.model.User;
import com.salesianos.triana.Miarma.users.repositorios.UserRepository;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

@RestController
@RequiredArgsConstructor
public class PublicacionController {

    private final PublicacionServiceImpl publicacionService;
    private final PublicacionRepository publicacionRepository;
    private final UserRepository userRepository;
    private final StorageService storageService;


    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado la publicación",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Publicacion.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha creado la publicación",
                    content = @Content),
    })


    @PostMapping("/post")
    public ResponseEntity<Publicacion> create(@RequestPart("publicacion")CreatePublicacionDto createPublicacionDto,@RequestPart("file")MultipartFile file,@AuthenticationPrincipal User user) throws IOException {

        String name = storageService.store(file);
        String extension = StringUtils.getFilenameExtension(name);
        BufferedImage originalImage = ImageIO.read(file.getInputStream());
        BufferedImage escaledImage = storageService.simpleResizer(originalImage, 1024);
        OutputStream outputStream = Files.newOutputStream(storageService.load(name));
        ImageIO.write(escaledImage,extension,outputStream);



        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(name)
                .toUriString();

        Publicacion publicacion10 = Publicacion.builder()
                .titulo(createPublicacionDto.getTitulo())
                .descripcion(createPublicacionDto.getDescripcion())
                .imagen(uri)
                .user(user)
                .build();


        return ResponseEntity.status(HttpStatus.CREATED).body(publicacionService.savePublicacion(createPublicacionDto,file,user));
    }

    @PutMapping("/post/{id}")
    public ResponseEntity<Publicacion> edit(@PathVariable Long id,@RequestPart("publicacion") CreatePublicacionDto createPublicacionDto,@RequestPart("file") MultipartFile file, CreateUserDto createUserDto) {
        return ResponseEntity.ok().body(publicacionService.edit(id, createPublicacionDto,file,createUserDto));



    }






}
