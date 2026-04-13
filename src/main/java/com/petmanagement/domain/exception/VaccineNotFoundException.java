package com.petmanagement.domain.exception;
public class VaccineNotFoundException extends DomainException {
    public VaccineNotFoundException(String id) { super("Vacuna no encontrada con ID: " + id); }
}
