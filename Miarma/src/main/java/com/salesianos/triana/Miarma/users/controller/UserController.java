package com.salesianos.triana.Miarma.users.controller;




import com.salesianos.triana.Miarma.services.StorageService;
import com.salesianos.triana.Miarma.users.dto.CreateUserDto;
import com.salesianos.triana.Miarma.users.dto.UserDtoConverter;
import com.salesianos.triana.Miarma.users.model.User;
import com.salesianos.triana.Miarma.users.services.UserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
;import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserDtoConverter userDtoConverter;
    private final StorageService storageService;


    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado el usuario",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha creado el usuario",
                    content = @Content),
    })

    @PostMapping("/auth/registrer")
    public ResponseEntity<?> create(@RequestPart("user") CreateUserDto newUser,@RequestPart("file") MultipartFile file) throws IOException {

        User userSaved = userService.saveUser(newUser,file);

        if (userSaved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(userDtoConverter.userToGetUserDto(userSaved));


    }

    /*
    @PutMapping("/profile/me")
    public ResponseEntity<CreateUserDto> edit(@RequestPart("user") CreateUserDto editUser, @PathVariable UUID id,@RequestPart("file") MultipartFile file){
        userService.edit(editUser,id,file);
        return ResponseEntity.ok().body(editUser);
    }

     */


}
