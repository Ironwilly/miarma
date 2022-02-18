package com.salesianos.triana.Miarma.dto;

import com.salesianos.triana.Miarma.models.Publicacion;

import org.springframework.stereotype.Component;

@Component
public class PublicacionDtoConverter {

    public Publicacion createPublicacionDto(CreatePublicacionDto c){

        return new Publicacion(
                c.getTitulo(),
                c.getDescripcion(),
                c.getImagen(),
                c.getIsPublic()


        );


    }

    public GetPublicacionDto getPublicacionToPublicacionDto(Publicacion publi){

        return  GetPublicacionDto.builder()

                .titulo(publi.getTitulo())
                .descripcion(publi.getDescripcion())
                .imagen(publi.getImagen())
                .isPublic(publi.getIsPublic())
                .userNick(publi.getUser().getNick())


                .build();
    }


}
