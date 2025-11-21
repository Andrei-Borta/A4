package model.adt;

import java.util.List;

public interface IList<T> {
    void add(T value);

    void clear();

    List<T> getAll();
}
