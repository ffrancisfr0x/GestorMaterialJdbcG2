package data;

import java.util.List;

public interface DAO<T> {
    T find(Long id);
    List<T> findAll();
    T save(T entity);
    public void update(T entity);
    public void delete(Long id);

}
