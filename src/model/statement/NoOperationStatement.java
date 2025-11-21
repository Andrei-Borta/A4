package model.statement;

import exceptions.LanguageInterpreterException;
import model.state.ProgramState;

public class NoOperationStatement implements IStatement {
    @Override
    public String  toString()
    {
        return "";
    }

    @Override
    public ProgramState execute(ProgramState state) throws LanguageInterpreterException {
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new NoOperationStatement();
    }
}
