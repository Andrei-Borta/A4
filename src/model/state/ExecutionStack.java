package model.state;

import exceptions.LanguageInterpreterADTException;
import model.adt.IStack;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ExecutionStack<T> implements IStack<T> {
    private final List<T> stack = new LinkedList<>();

    @Override
    public void push(T statement) {
        stack.addFirst(statement);
    }

    @Override
    public T pop() throws LanguageInterpreterADTException {
        if (stack.isEmpty()) {
            throw new LanguageInterpreterADTException("Stack is empty");
        }
        return stack.removeFirst();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int size() {
        return stack.size();
    }

    @Override
    public List<T> getAll() {
        List<T> list = new java.util.ArrayList<>(stack.stream().toList());
        return list;
    }
}
