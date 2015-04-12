package com.project.dao;

/**
 * Інтерфейс базових опреацій над класами - відображеннями таблиць у БД. Усі класи,
 * що реалізують данний інтерфейс - DAO класів-суттевостей.
 * @param <E> тип класу-суттевості
 */
public interface CRUD<E> {

    /**
     * Створееня обекту у БД
     * @param object оперуючий об'єкт
     * @return id щойно створеного об'єкту
     */
    public long create(E object);

    /**
     * Отримання обекту з БД
     * @param id id об'єкту, що треба отримати
     * @return шуканий об'єкт
     */
    public E get(long id);

    /**
     * Оновлення обекту у БД
     * @param object оперуючий об'єкт
     */
    public void update(E object);

    /**
     * Видалення обекту у БД
     * @param object оперуючий об'єкт
     */
    public void delete(E object);
    
}
