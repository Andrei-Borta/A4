package model.state;

import exceptions.LanguageInterpreterADTException;
import exceptions.LanguageInterpreterException;
import model.adt.IHeap;
import model.value.IValue;

import java.util.HashMap;
import java.util.Map;

public class Heap implements IHeap<Integer, IValue> {

    private final Map<Integer, IValue> content;
    private int freeLocation;

    public Heap() {
        this.content = new HashMap<>();
        this.freeLocation = 1;
    }

    private int getFreeAddress() {
        while (content.containsKey(freeLocation)) {
            freeLocation++;
        }
        return freeLocation;
    }

    @Override
    public int allocate(IValue value) throws LanguageInterpreterADTException {
        if (value == null)
            throw new LanguageInterpreterADTException("Cannot allocate null value in heap.");

        int address = getFreeAddress();
        content.put(address, value);
        freeLocation++;
        return address;
    }

    @Override
    public IValue get(int address) throws LanguageInterpreterADTException {
        if (address == 0)
            throw new LanguageInterpreterADTException("Invalid heap address 0 (null reference).");

        if (!content.containsKey(address))
            throw new LanguageInterpreterADTException("Heap read error: address " + address + " does not exist.");


        return content.get(address);
    }

    @Override
    public void put(int address, IValue value) throws LanguageInterpreterADTException {
        if (address == 0)
            throw new LanguageInterpreterADTException("Invalid heap address 0 for write.");

        if (!content.containsKey(address))
            throw new LanguageInterpreterADTException("Heap write error: address " + address + " does not exist.");

        content.put(address, value);
    }

    @Override
    public boolean isDefined(int address) {
        return content.containsKey(address);
    }

    @Override
    public Map<Integer, IValue> getAll() {
        return content;
    }

    @Override
    public String toString() {
        return content.toString();
    }

    @Override
    public void remove(int address) throws LanguageInterpreterADTException {
        if (address == 0)
            throw new LanguageInterpreterADTException("Cannot delete address 0 (null).");

        if (!content.containsKey(address))
            throw new LanguageInterpreterADTException("Heap delete error: address " + address + " not found.");

        content.remove(address);
    }

    @Override
    public void setContent(Map<Integer, IValue> newContent) throws  LanguageInterpreterADTException {
        if (newContent == null)
            throw new LanguageInterpreterADTException("Cannot set heap content to null.");

        content.clear();
        content.putAll(newContent);

        freeLocation = content.keySet().stream()
                .max(Integer::compareTo)
                .map(i -> i + 1)
                .orElse(1);
    }
}
