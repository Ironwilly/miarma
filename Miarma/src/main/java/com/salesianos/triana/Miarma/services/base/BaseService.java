package com.salesianos.triana.Miarma.services.base;


import com.salesianos.triana.Miarma.errors.exceptions.SingleEntityNotFoundException;
import com.salesianos.triana.Miarma.services.StorageService;
import com.salesianos.triana.Miarma.services.impl.FileSystemStorageService;
import com.salesianos.triana.Miarma.users.dto.CreateUserDto;
import com.salesianos.triana.Miarma.users.model.User;
import com.salesianos.triana.Miarma.users.repositorios.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class BaseService<T,ID,R extends JpaRepository<T,ID>> {




    @Autowired
    protected R repositorio;


    public List<T> findAll() {
        return repositorio.findAll();
    }

    public Optional<T> findById(ID id) {
        return repositorio.findById(id);
    }

    public T save(T t) {
        return repositorio.save(t);
    }


    public T edit(T t) {

        return save(t);
    }





    public void delete(T t) {
        repositorio.delete(t);
    }

    public void deleteById(ID id) {
        repositorio.deleteById(id);
    }



}