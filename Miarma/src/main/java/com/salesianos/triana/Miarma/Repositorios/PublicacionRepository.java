package com.salesianos.triana.Miarma.Repositorios;

import com.salesianos.triana.Miarma.dto.CreatePublicacionDto;
import com.salesianos.triana.Miarma.models.Publicacion;
import com.salesianos.triana.Miarma.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

public interface PublicacionRepository extends JpaRepository<Publicacion,Long> {


    //List<Publicacion> findAll (Publicacion publicacion);

}
