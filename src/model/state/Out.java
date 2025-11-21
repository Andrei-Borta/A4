package model.state;

import model.adt.IList;

import java.util.ArrayList;
import java.util.List;

public class Out<T> implements IList<T> {
    private final List<T> outputList = new ArrayList<>();

    @Override
    public void add(T value) {
        outputList.add(value);
    }

    @Override
    public void clear() {
        outputList.clear();
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<>(outputList);
    }

    @Override
    public String toString() {
        return outputList.toString();
    }
}
