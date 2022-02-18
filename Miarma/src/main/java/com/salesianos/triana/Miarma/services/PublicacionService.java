package com.salesianos.triana.Miarma.services;


import com.salesianos.triana.Miarma.dto.CreatePublicacionDto;

import com.salesianos.triana.Miarma.models.Publicacion;

import com.salesianos.triana.Miarma.users.dto.CreateUserDto;
import com.salesianos.triana.Miarma.users.model.User;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface PublicacionService {

    List<Publicacion> listarPublicaciones();
    Optional<Publicacion> findOne(Long id);
    Publicacion savePublicacion(CreatePublicacionDto createPublicacionDto, MultipartFile file, User user);
    public void deleteFile(String filename);
    Publicacion edit(Long id, CreatePublicacionDto createPublicacionDto, MultipartFile file,CreateUserDto createUserDto);


}
