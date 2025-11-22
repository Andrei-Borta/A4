package model.adt;

import model.value.IValue;

import java.util.Map;

public interface IHeap<K, V> {
    int allocate(V value);
    V get(int address);
    void put(int address, V value);
    boolean isDefined(int address);
    Map<K, V> getAll();
    void remove(int address);
    void setContent(Map<Integer, IValue> newContent);
}
