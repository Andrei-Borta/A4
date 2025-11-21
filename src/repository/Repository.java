package repository;

import exceptions.LanguageInterpreterException;
import model.state.ProgramState;
import model.statement.IStatement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private final List<ProgramState> programStates = new ArrayList<>();
    private final String logFilePath;

    public Repository(ProgramState initialProgram, String logFilePath) {
        programStates.add(initialProgram);
        this.logFilePath = logFilePath;
    }

    @Override
    public ProgramState getCurrentProgramState() {
        return programStates.getFirst();
    }

    @Override
    public List<ProgramState> getPrgList() {
        return programStates;
    }

    @Override
    public void addPrg(ProgramState prgState) {
        programStates.add(prgState);
    }

    @Override
    public void pushStatement(IStatement statement) {
        ProgramState currentProgramState = getCurrentProgramState();
        currentProgramState.pushStatement(statement);
    }

    @Override
    public void logPrgStateExec() throws LanguageInterpreterException {
        try(var logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))) {
            ProgramState prg = this.getCurrentProgramState();

            logFile.println("ExeStack:");
            for (var stmt: prg.executionStack().getAll())
                logFile.println(stmt.toString());

            logFile.println("SymTable:");
            prg.symbolTable().getAll().forEach((k, v) -> logFile.println(k + " --> " + v.toString()));

            logFile.println("Out:");
            prg.out().getAll().forEach(v -> logFile.println(v.toString()));

            logFile.println("FileTable:");
            prg.fileTable().getAll().forEach((fd, fn) -> logFile.println(fn));

            logFile.println("-----------");
            logFile.flush();
        }
        catch (IOException e) {
            throw new LanguageInterpreterException(e.getMessage());
        }
    }

}
