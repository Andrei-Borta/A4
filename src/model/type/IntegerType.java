package model.type;

import model.value.IValue;
import model.value.IntegerValue;

public class IntegerType implements IType {
    @Override
    public IValue getDefaultValue() {
        return new IntegerValue(0);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof IntegerType;
    }

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public IType deepCopy() {
        return new IntegerType();
    }
}
