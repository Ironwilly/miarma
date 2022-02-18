package com.salesianos.triana.Miarma.services.impl;

import com.salesianos.triana.Miarma.Repositorios.PublicacionRepository;
import com.salesianos.triana.Miarma.dto.CreatePublicacionDto;
import com.salesianos.triana.Miarma.dto.PublicacionDtoConverter;
import com.salesianos.triana.Miarma.errors.exceptions.EntityNotFoundException;
import com.salesianos.triana.Miarma.models.Publicacion;
import com.salesianos.triana.Miarma.services.PublicacionService;
import com.salesianos.triana.Miarma.services.StorageService;
import com.salesianos.triana.Miarma.services.base.BaseService;
import com.salesianos.triana.Miarma.users.dto.CreateUserDto;
import com.salesianos.triana.Miarma.users.model.User;
import com.salesianos.triana.Miarma.users.repositorios.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
public class PublicacionServiceImpl implements PublicacionService {




    private final StorageService storageService;
    private final PublicacionRepository publicacionRepository;


    public PublicacionServiceImpl(StorageService storageService, PublicacionRepository publicacionRepository) {

        this.storageService = storageService;
        this.publicacionRepository = publicacionRepository;
    }

    @Override
    public List<Publicacion> listarPublicaciones(){
        return publicacionRepository.findAll();
    }

    @Override
    public Optional<Publicacion> findOne(Long id){
        return publicacionRepository.findById(id);
    }

    @Override
    public Publicacion savePublicacion(CreatePublicacionDto createPublicacionDto, MultipartFile file, User user) {

        String filename = storageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(filename)
                .toUriString();

        return publicacionRepository.save(Publicacion.builder()
                        .titulo(createPublicacionDto.getTitulo())
                        .descripcion(createPublicacionDto.getDescripcion())
                        .imagen(uri)
                        .isPublic(createPublicacionDto.getIsPublic())
                        .user(user)
                        .build());




    }




    @Override
    public void deleteFile(String filename) {

    }


    @Override
    public Publicacion edit(Long id, CreatePublicacionDto createPublicacionDto, MultipartFile file,CreateUserDto createUserDto){



        String filename = storageService.store(file);


        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(filename)
                .toUriString();

         publicacionRepository.save(Publicacion.builder()


                .titulo(createPublicacionDto.getTitulo())
                .descripcion(createPublicacionDto.getDescripcion())
                .imagen(uri)
                .isPublic(createPublicacionDto.getIsPublic())
                .build());

        Optional<Publicacion> p = publicacionRepository.findById(id);
        if(p.isEmpty()){
            throw new EntityNotFoundException(id,Publicacion.class);
        }
        return p.map(nuevo ->{
            nuevo.setTitulo(createPublicacionDto.getTitulo());
            nuevo.setImagen(createPublicacionDto.getImagen());
            nuevo.setDescripcion(createPublicacionDto.getDescripcion());
            return nuevo;

        }).get();



    }




}
