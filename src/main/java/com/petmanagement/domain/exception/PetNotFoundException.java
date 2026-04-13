package com.petmanagement.domain.exception;
public class PetNotFoundException extends DomainException {
    public PetNotFoundException(String id) { super("Mascota no encontrada con ID: " + id); }
}
