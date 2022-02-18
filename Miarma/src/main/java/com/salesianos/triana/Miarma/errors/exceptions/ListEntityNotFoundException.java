package com.salesianos.triana.Miarma.errors.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class ListEntityNotFoundException extends EntityNotFoundException{


        public ListEntityNotFoundException(Class clazz){
            super(String.format("No se pueden encontrar del tipo %s  ",clazz.getName()));


        }




}
