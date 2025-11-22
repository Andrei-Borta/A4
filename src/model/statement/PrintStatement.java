package model.statement;

import exceptions.LanguageInterpreterException;
import model.expression.IExpression;
import model.state.ProgramState;

public record PrintStatement(IExpression expression) implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) throws LanguageInterpreterException {
        var symbolTable = state.symbolTable();
        var heap = state.heap();

        state.out().add(expression.evaluate(symbolTable, heap));
        return state;
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }

    @Override
    public IStatement deepCopy() {
        return new PrintStatement(expression.deepCopy());
    }
}
