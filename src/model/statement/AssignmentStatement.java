package model.statement;

import exceptions.LanguageInterpreterADTException;
import exceptions.LanguageInterpreterException;
import model.expression.IExpression;
import model.adt.IDictionary;
import model.state.ProgramState;
import model.value.IValue;

public record AssignmentStatement(IExpression expression, String variableName)
        implements IStatement {

    @Override
    public String toString() {
        return variableName + "=" + expression.toString();
    }

    @Override
    public IStatement deepCopy() {
        return new AssignmentStatement(expression.deepCopy(), variableName);
    }

    @Override
    public ProgramState execute(ProgramState state) throws LanguageInterpreterException {
        IDictionary<String, IValue> symbolTable = state.symbolTable();

        try {
            IValue value = expression.evaluate(symbolTable);

            if (!value.getType().equals(symbolTable.getValue(variableName).getType())) {
                throw new LanguageInterpreterException("Variable type mismatch");
            }

            symbolTable.update(variableName, value);
            return state;
        }
        catch (LanguageInterpreterADTException e) {
            throw new LanguageInterpreterException(e.getMessage());
        }
    }
}
