package model.statement;

import exceptions.LanguageInterpreterException;
import model.state.ProgramState;

public record CompoundStatement(IStatement first, IStatement second) implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) throws LanguageInterpreterException {
        var stack = state.executionStack();
        stack.push(second);
        stack.push(first);
        return state;
    }

    @Override
    public String toString() {
        return first.toString() + "; " + second.toString();
    }

    @Override
    public IStatement deepCopy() {
        return new CompoundStatement(first.deepCopy(), second.deepCopy());
    }

}
