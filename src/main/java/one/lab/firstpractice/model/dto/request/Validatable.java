package one.lab.firstpractice.model.dto.request;

import one.lab.firstpractice.exception.exceptions.ResourceAlreadyExistException;

public interface Validatable<T extends CreateRequest> {

    String EMPTY_STRING = "";

    void checkExistenceForCreation(T request) throws ResourceAlreadyExistException;
}