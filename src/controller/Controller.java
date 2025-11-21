package controller;

import exceptions.LanguageInterpreterException;
import model.adt.IStack;
import model.state.ProgramState;
import model.statement.IStatement;
import model.value.IValue;
import repository.IRepository;

public class Controller {
    private final IRepository repository;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public ProgramState oneStep(ProgramState programState, boolean display) throws LanguageInterpreterException {
        IStack<IStatement> stack = programState.executionStack();

        if (stack.isEmpty())
            throw new LanguageInterpreterException("Execution stack is empty");

        if(display && stack.size() == 1) {
            IO.println(programState.toString());
        }

        IStatement statement = stack.pop();

        return statement.execute(programState);
    }

    public void allSteps(boolean display) throws LanguageInterpreterException {
        ProgramState currentState = repository.getCurrentProgramState();
        repository.logPrgStateExec();

        while(!currentState.executionStack().isEmpty()) {
            oneStep(currentState, display);
            repository.logPrgStateExec();
        }

        if (display) {
            IO.print(currentState.toString());
        }
    }

    public ProgramState getCurrentProgramState() {
        return repository.getCurrentProgramState();
    }

    public void pushStatement(IStatement statement) {
        repository.pushStatement(statement);
    }

    public void clearOut() {
        repository.getCurrentProgramState().clearOut();
    }

    public void clearSymbolTable() {
        repository.getCurrentProgramState().clearSymbolTable();
    }

    public String displayCurrentProgramState() { return repository.getCurrentProgramState().toString(); }
}
