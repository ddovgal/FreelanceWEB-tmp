package com.project.dao;

public interface CRUD<E> {
    
    public long create(E object);
    public E get(long id);
    public void update(E object);
    public void delete(E object);
    
}
