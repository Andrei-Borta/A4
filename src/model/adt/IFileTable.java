package model.adt;

import exceptions.LanguageInterpreterADTException;
import model.value.StringValue;
import java.io.BufferedReader;

public interface IFileTable extends IDictionary<StringValue, BufferedReader> {
    void remove(StringValue key) throws LanguageInterpreterADTException;
}
