package com.petmanagement.domain.exception;
public class MedicalRecordNotFoundException extends DomainException {
    public MedicalRecordNotFoundException(String id) { super("Historial médico no encontrado con ID: " + id); }
}
