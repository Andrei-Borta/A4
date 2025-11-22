package model.expression;

import exceptions.LanguageInterpreterException;
import model.adt.IDictionary;
import model.adt.IHeap;
import model.value.IValue;
import model.value.ReferenceValue;

public record HeapReadingExpression(IExpression expression) implements IExpression {

    @Override
    public IValue evaluate(IDictionary<String, IValue> symbolTable,
                           IHeap<Integer, IValue> heap) throws LanguageInterpreterException {

        IValue value = expression.evaluate(symbolTable, heap);

        if (!(value instanceof ReferenceValue refValue))
            throw new LanguageInterpreterException(
                    "Expression " + expression + " evaluated to a non-reference value: " + value);

        int address = refValue.address();

        if (!heap.isDefined(address))
            throw new LanguageInterpreterException(
                    "Address " + address + " is not allocated.");

        return heap.get(address);
    }

    @Override
    public String toString() {
        return "readHeap(" + expression + ")";
    }

    @Override
    public IExpression deepCopy() {
        return new HeapReadingExpression(expression.deepCopy());
    }
}
