package model.state;

import exceptions.LanguageInterpreterADTException;
import model.adt.IFileTable;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileTable implements IFileTable {
    private final Map<StringValue, BufferedReader> map = new HashMap<>();

    @Override
    public boolean isDefined(StringValue fileName) {
        return map.containsKey(fileName);
    }

    @Override
    public void declareVariable(StringValue filename, BufferedReader fileDescriptor)
            throws LanguageInterpreterADTException {
        if (map.containsKey(filename)) {
            throw new LanguageInterpreterADTException("File already opened: " + filename);
        }
        map.put(filename, fileDescriptor);
    }

    @Override
    public void update(StringValue filename, BufferedReader fileDescriptor)
            throws LanguageInterpreterADTException {
        if (!map.containsKey(filename)) {
            throw new LanguageInterpreterADTException("File not opened: " + filename);
        }
        map.put(filename, fileDescriptor);
    }

    @Override
    public void remove(StringValue key) throws LanguageInterpreterADTException {
        if (!isDefined(key)) {
            throw new LanguageInterpreterADTException("File not opened: " + key.value());
        }

        BufferedReader br = map.get(key);
        try {
            br.close();
        } catch (IOException e) {
            throw new LanguageInterpreterADTException("Error closing file " + key.value() + ": " + e.getMessage());
        }
        map.remove(key);
    }

    @Override
    public BufferedReader getValue(StringValue filename)
            throws LanguageInterpreterADTException {
        if (!map.containsKey(filename)) {
            throw new LanguageInterpreterADTException("File not opened: " + filename);
        }
        return map.get(filename);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Map<StringValue, BufferedReader> getAll() {
        return new HashMap<>(map);
    }
}
