package model.statement;

import exceptions.LanguageInterpreterException;
import model.expression.IExpression;
import model.state.ProgramState;
import model.type.IType;
import model.type.ReferenceType;
import model.value.IValue;
import model.value.ReferenceValue;

public record HeapWritingStatement(String variableName, IExpression expression) implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) throws LanguageInterpreterException {

        var symbolTable = state.symbolTable();
        var heap = state.heap();

        if (!symbolTable.isDefined(variableName))
            throw new LanguageInterpreterException(
                    "Variable '" + variableName + "' is not declared.");

        IValue varValue = symbolTable.getValue(variableName);

        if (!(varValue instanceof ReferenceValue refValue))
            throw new LanguageInterpreterException(
                    "Variable '" + variableName + "' is not a reference.");

        int address = refValue.address();
        ReferenceType refType = (ReferenceType) refValue.getType();
        IType locationType = refType.inner();

        if (!heap.isDefined(address))
            throw new LanguageInterpreterException(
                    "Address " + address + " is not allocated.");

        IValue evaluated = expression.evaluate(symbolTable, heap);

        if (!evaluated.getType().equals(locationType))
            throw new LanguageInterpreterException(
                    "Type mismatch. Expected: " + locationType + ", got: " + evaluated.getType());

        heap.put(address, evaluated);

        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new HeapWritingStatement(variableName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return "heapWrite(" + variableName + ", " + expression + ")";
    }
}
