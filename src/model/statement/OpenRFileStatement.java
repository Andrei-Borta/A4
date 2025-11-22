package model.statement;

import exceptions.LanguageInterpreterADTException;
import exceptions.LanguageInterpreterException;
import model.expression.IExpression;
import model.state.ProgramState;
import model.type.StringType;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public record OpenRFileStatement(IExpression expression) implements IStatement {

    @Override
    public String toString() {
        return "open(" + expression.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws LanguageInterpreterException {
        var fileTable = state.fileTable();
        var symbolTable = state.symbolTable();
        var heap = state.heap();

        IValue value;
        try {
            value = expression.evaluate(symbolTable, heap);
        }
        catch (LanguageInterpreterADTException e) {
            throw new LanguageInterpreterException(e.getMessage());
        }

        if (!value.getType().equals(new StringType())) {
            throw new LanguageInterpreterException("Expression does not evaluate to a string.");
        }

        StringValue fileName = (StringValue) value;

        if (fileTable.isDefined(fileName)) {
            throw new LanguageInterpreterException("File already opened: " + fileName.value());
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName.value()));
            fileTable.declareVariable(fileName, br);
        } catch (IOException e) {
            throw new LanguageInterpreterException("Could not open file " + fileName.value() + " -> " + e.getMessage());
        }

        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new OpenRFileStatement(expression.deepCopy());
    }
}
