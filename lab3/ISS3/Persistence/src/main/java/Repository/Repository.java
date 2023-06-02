package Repository;

import Domain.Entity;

import java.util.List;

public interface Repository<TID,E extends Entity<TID>>  {
    E findID(TID id);
    List<E> getAll();
    E add(E e);
    E remove(E e);
    E update(E e);
}
