package model.adt;

import exceptions.LanguageInterpreterADTException;
import model.statement.IStatement;

import java.util.List;

public interface IStack<T> {
    void push(T statement);

    T pop() throws LanguageInterpreterADTException;

    boolean isEmpty();

    int size();

    List<T> getAll();
}
