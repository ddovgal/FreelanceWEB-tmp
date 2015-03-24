package com.project.dao;

public interface CRUD<E> {
    
    public E create(E object);
    public E get(int id);
    public void update(E object);
    public void delete(E object);
    
}
