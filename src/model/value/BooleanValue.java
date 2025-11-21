package model.value;

import model.type.IType;
import model.type.BooleanType;

public record BooleanValue(boolean value) implements IValue {

    @Override
    public IType getType() {
        return new BooleanType();
    }

    @Override
    public IValue deepCopy() {
        return new BooleanValue(value);
    }

    @Override
    public String toString(){
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof BooleanValue(boolean _value))) return false;
        return this.value == _value;
    }
}
