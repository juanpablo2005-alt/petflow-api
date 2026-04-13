package com.petmanagement.domain.exception;
public class CategoryNotFoundException extends DomainException {
    public CategoryNotFoundException(String id) { super("Categoría no encontrada con ID: " + id); }
}
