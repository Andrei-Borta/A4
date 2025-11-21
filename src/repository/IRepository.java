package repository;

import exceptions.LanguageInterpreterException;
import model.state.ProgramState;
import model.statement.IStatement;

import java.util.List;

public interface IRepository {
    ProgramState getCurrentProgramState();
    public void pushStatement(IStatement statement);
    public List<ProgramState> getPrgList();
    public void addPrg(ProgramState prgState);
    void logPrgStateExec() throws LanguageInterpreterException;
}
