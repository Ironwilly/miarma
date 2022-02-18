package com.salesianos.triana.Miarma.models;


import com.salesianos.triana.Miarma.users.model.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Publicacion {

    @Id
    @GeneratedValue
    private Long id;

    private String titulo;

    @Lob
    private String descripcion;

    private String imagen;

    private Boolean isPublic;


    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    public Publicacion(String titulo, String descripcion, String imagen, Boolean isPublic) {

        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.isPublic = isPublic;
    }

    public void addUser (User u){

        this.user = u;
        u.getPublicaciones().add(this);
    }

    public void removeUser(User u) {
        u.getPublicaciones().remove(this);
        this.user = null;
    }



}
