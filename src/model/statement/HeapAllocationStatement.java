package model.statement;

import exceptions.LanguageInterpreterException;
import model.expression.IExpression;
import model.state.ProgramState;
import model.type.IType;
import model.type.ReferenceType;
import model.value.IValue;
import model.value.ReferenceValue;

public record HeapAllocationStatement(String variableName, IExpression expression) implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) throws LanguageInterpreterException {

        var symbolTable = state.symbolTable();
        var heap = state.heap();

        if (!symbolTable.isDefined(variableName))
            throw new LanguageInterpreterException(
                    "Variable '" + variableName + "' is not declared.");

        IValue value = symbolTable.getValue(variableName);
        IType valueType = value.getType();

        if (!(valueType.getDefaultValue() instanceof ReferenceValue refValueDefault))
            throw new LanguageInterpreterException(
                    "Variable '" + variableName + "' must have a ReferenceType.");

        ReferenceType refType = (ReferenceType) valueType;
        IType expectedInnerType = refType.inner();

        IValue evaluated = expression.evaluate(symbolTable, heap);

        if (!evaluated.getType().equals(expectedInnerType))
            throw new LanguageInterpreterException(
                    "Type mismatch. Expected: " + expectedInnerType + " but got: " + evaluated.getType());

        int newAddress = heap.allocate(evaluated);

        symbolTable.update(variableName, new ReferenceValue(newAddress, expectedInnerType));

        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new HeapAllocationStatement(variableName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return "new(" + variableName + ", " + expression + ")";
    }
}
