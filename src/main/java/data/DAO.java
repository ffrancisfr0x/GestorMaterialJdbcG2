package data;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    Optional<T> find(Long id);
    List<T> findAll();
    T save(T entity);
    public void update(T entity);
    public void delete(Long id);

}
