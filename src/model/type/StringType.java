package model.type;

import model.value.IValue;
import model.value.StringValue;

public class StringType implements IType {
    @Override
    public IValue getDefaultValue() { return new StringValue(""); }

    @Override
    public boolean equals(Object obj) { return obj instanceof StringType; }

    @Override
    public String toString() { return "string"; }

    @Override
    public IType deepCopy() { return new StringType(); }
}
