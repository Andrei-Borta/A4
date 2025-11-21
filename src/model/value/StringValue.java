package model.value;

import model.type.IType;
import model.type.StringType;

import java.util.Objects;

public record StringValue(String value) implements IValue {

    @Override
    public IType getType() { return new StringType(); }

    @Override
    public IValue deepCopy() {
        return new StringValue(value);
    }

    @Override
    public String toString() { return "\"" + value + "\""; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof StringValue(String _value))) return false;
        return Objects.equals(this.value, _value);
    }
}
