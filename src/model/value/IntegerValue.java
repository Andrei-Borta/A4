package model.value;

import model.type.IntegerType;
import model.type.IType;

public record IntegerValue(int value) implements IValue {

    @Override
    public IType getType() {
        return new IntegerType();
    }

    @Override
    public IValue deepCopy() {
        return new IntegerValue(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof IntegerValue(int _value))) return false;
        return this.value == _value;
    }
}
