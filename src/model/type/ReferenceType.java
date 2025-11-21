package model.type;

import model.value.IValue;
import model.value.ReferenceValue;

public record ReferenceType(IType inner) implements IType {

    @Override
    public IValue getDefaultValue() { return new ReferenceValue(0, inner); }

    @Override
    public boolean equals(Object obj) { return obj instanceof ReferenceType(IType inner1) && inner.equals(inner1); }

    @Override
    public String toString() { return "ref(" + inner.toString() + ")"; }

    @Override
    public IType deepCopy() { return new ReferenceType(inner.deepCopy()); }
}
