package com.salesianos.triana.Miarma.users.services;


import com.salesianos.triana.Miarma.Repositorios.PublicacionRepository;
import com.salesianos.triana.Miarma.errors.exceptions.SingleEntityNotFoundException;
import com.salesianos.triana.Miarma.services.StorageService;
import com.salesianos.triana.Miarma.services.base.BaseService;
import com.salesianos.triana.Miarma.users.dto.CreateUserDto;
import com.salesianos.triana.Miarma.users.dto.GetUserDto;
import com.salesianos.triana.Miarma.users.dto.UserDtoConverter;
import com.salesianos.triana.Miarma.users.model.Roles;
import com.salesianos.triana.Miarma.users.model.User;
import com.salesianos.triana.Miarma.users.repositorios.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service("userDetailService")
public class UserService extends BaseService<User, UUID, UserRepository> implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserDtoConverter userDtoConverter;
    private final StorageService storageService;
    private final UserRepository userRepository;
    private final PublicacionRepository publicacionRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.repositorio.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(email + " no encontrado"));
    }


    public List<User> loadUserByRole(Roles rol) throws UsernameNotFoundException{
        return this.repositorio.findByRol(rol);
    }

    public Optional<User> loadUserById(UUID id) throws UsernameNotFoundException{
        return this.repositorio.findById(id);
    }





    public User saveUser(CreateUserDto createUserDto, MultipartFile file) throws IOException {

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

        return userRepository.save(User.builder()
                .nombre(createUserDto.getNombre())
                .apellidos(createUserDto.getApellidos())
                .direccion(createUserDto.getDireccion())
                .email(createUserDto.getEmail())
                .telefono(createUserDto.getTelefono())
                .avatar(uri)
                .fechNaci(createUserDto.getFechNac())
                .nick(createUserDto.getNick())
                .isPrivado(Boolean.valueOf(createUserDto.getIsPrivado()))
                .password(passwordEncoder.encode(createUserDto.getPassword()))
                .rol(Roles.USER)
                .build());


    }

    public CreateUserDto editUser (CreateUserDto createUserDto, UUID id,MultipartFile file){

        String filename = storageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(filename)
                .toUriString();

        return  userRepository.findById(id).map(u -> {
            u.setNombre(createUserDto.getNombre());
            u.setApellidos(createUserDto.getApellidos());
            u.setNick(createUserDto.getNick());
            u.setFechNaci(createUserDto.getFechNac());
            u.setDireccion(createUserDto.getDireccion());
            u.setEmail(createUserDto.getEmail());
            u.setIsPrivado(Boolean.valueOf(createUserDto.getIsPrivado()));
            u.setAvatar(uri);
            userRepository.save(u);
            return userDtoConverter.editUser(u);
        }).orElseThrow(() -> new SingleEntityNotFoundException(id,User.class));

    }

    public List<GetUserDto> listUserToListGetUserDto(List<User> users) {
        List<GetUserDto> getUserDtos = new ArrayList<>();
        users.stream().forEach(u -> {
            getUserDtos.add(userDtoConverter.userToGetUserDto(u));
        });
        return getUserDtos;
    }



}

