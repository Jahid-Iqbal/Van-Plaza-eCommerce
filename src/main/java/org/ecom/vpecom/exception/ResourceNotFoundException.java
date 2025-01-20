package org.ecom.vpecom.exception;

public class ResourceNotFoundException extends RuntimeException{

    String resourceName;
    String field;
    String fieldName;
    long fieldId;

    public ResourceNotFoundException(String message, Throwable cause, String resourceName, String field, String fieldName) {
        super(String.format("%s not found with %s : %s", resourceName, field, fieldName));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldName = fieldName;
    }

    public ResourceNotFoundException(String resourceName, String fieldName, long fieldId) {
        super(String.format("%s not found with %s : %d", resourceName, fieldName, fieldId));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldId = fieldId;
    }

    public ResourceNotFoundException(){}

}
