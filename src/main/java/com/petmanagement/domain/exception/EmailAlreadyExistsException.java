package com.petmanagement.domain.exception;
public class EmailAlreadyExistsException extends DomainException {
    public EmailAlreadyExistsException(String email) { super("El email ya está registrado: " + email); }
}
