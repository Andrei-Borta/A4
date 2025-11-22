package model.expression;

import exceptions.LanguageInterpreterADTException;
import exceptions.LanguageInterpreterException;
import model.adt.IDictionary;
import model.adt.IHeap;
import model.value.IValue;

public record VariableExpression(String variableName) implements IExpression {

    @Override
    public String toString() {
        return variableName;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> symTable, IHeap<Integer, IValue> heap) throws LanguageInterpreterException {
        try {
            return symTable.getValue(variableName);
        }
        catch (LanguageInterpreterADTException e) {
            throw new LanguageInterpreterException(e.getMessage());
        }
    }

    @Override
    public IExpression deepCopy() {
        return new VariableExpression(variableName);
    }

}
