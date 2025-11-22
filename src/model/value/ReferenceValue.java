package model.value;

import model.type.IType;
import model.type.ReferenceType;

public record ReferenceValue(int address, IType locationType) implements IValue {

    @Override
    public IType getType() { return new ReferenceType(locationType); }

    @Override
    public IValue deepCopy() { return new ReferenceValue(address, locationType.deepCopy()); }

    @Override
    public String toString() { return "heap(" + address + "," + locationType.toString() + ")"; }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ReferenceValue(int address1, IType type) &&
                this.address == address1 &&
                this.locationType.equals(type);
    }
}
