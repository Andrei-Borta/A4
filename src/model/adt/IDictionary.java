package model.adt;

import exceptions.LanguageInterpreterADTException;

import java.util.Map;

public interface IDictionary<K, V> {
    boolean isDefined(K variableName);

    void declareVariable(K variableName, V type) throws LanguageInterpreterADTException;

    void update(K variableName, V value)  throws LanguageInterpreterADTException;

    V getValue(K variableName)  throws LanguageInterpreterADTException;

    void clear();

    Map<K, V> getAll();
}
