package model.state;

import exceptions.LanguageInterpreterADTException;
import model.adt.IDictionary;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable<K, V> implements IDictionary<K, V> {
    private final Map<K, V> map = new HashMap<>();

    @Override
    public boolean isDefined(K variableName) {
        return map.containsKey(variableName);
    }

    @Override
    public void declareVariable(K variableName, V value)  throws LanguageInterpreterADTException {
        if (map.containsKey(variableName)) {
            throw new LanguageInterpreterADTException("Variable already defined");
        }
        map.put(variableName, value);
    }

    @Override
    public void update(K variableName, V value)  throws LanguageInterpreterADTException {
        if (!map.containsKey(variableName)) {
            throw new LanguageInterpreterADTException("Variable not defined");
        }
        map.put(variableName, value);
    }

    @Override
    public V getValue(K variableName)  throws LanguageInterpreterADTException {
        if (!map.containsKey(variableName)) {
            throw new LanguageInterpreterADTException("Variable not defined");
        }
        return map.get(variableName);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Map<K, V> getAll() {
        return new HashMap<>(map);
    }
}
