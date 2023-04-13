package restaurant.server.repository;

import java.util.List;

/**
 *
 * @author Natasa Todorov Markovic
 */
public interface GenericRepository<T, Id> {

    List<T> getAll() throws Exception;

    void add(T entity) throws Exception;

    void update(T entity) throws Exception;

    void delete(T entity) throws Exception;

    T findById(Id id) throws Exception;

    List<T> findByQuery(String query) throws Exception;
}
