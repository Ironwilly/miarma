package com.salesianos.triana.Miarma.errors.exceptions;

import java.util.UUID;

public class SingleEntityNotFoundException extends EntityNotFoundException{



    public SingleEntityNotFoundException(UUID id, Class clazz) {
        super(String.format("No se puede encontrar una entidad del tipo %s con ID: %s", clazz.getName(), id));
    }

    public SingleEntityNotFoundException(Long id, Class clazz) {
        super(String.format("No se puede encontrar una entidad del tipo %s con ID: %s", clazz.getName(), id));
    }
}
