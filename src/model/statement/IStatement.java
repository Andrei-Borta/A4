package model.statement;

import exceptions.LanguageInterpreterException;
import model.state.ProgramState;

public interface IStatement {
    ProgramState execute(ProgramState state) throws LanguageInterpreterException;

    IStatement deepCopy();
}
