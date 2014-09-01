package com.mashape.testtask.todo.api.exception;

/**
 * This exception can be used by Indexing provider
 * <p/>
 * Created by Alex Manusovich on 8/30/14.
 */
public class IndexingException extends TodoApplicationException {
    public IndexingException(String message) {
        super(ApplicationExceptionCode.INDEXING, message);
    }
}
