package model.statement;

import exceptions.LanguageInterpreterADTException;
import exceptions.LanguageInterpreterException;
import model.expression.IExpression;
import model.state.ProgramState;
import model.type.StringType;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public record CloseRFileStatement(IExpression expression) implements IStatement {

    @Override
    public String toString() {
        return "close(" + expression + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws LanguageInterpreterException {
        var symTable = state.symbolTable();
        var fileTable = state.fileTable();

        IValue value;
        try {
            value = expression.evaluate(symTable);
        } catch (LanguageInterpreterADTException e) {
            throw new LanguageInterpreterException(e.getMessage());
        }

        if (!value.getType().equals(new StringType())) {
            throw new LanguageInterpreterException("Expression does not evaluate to a string.");
        }

        StringValue fileName = (StringValue) value;

        if (!fileTable.isDefined(fileName)) {
            throw new LanguageInterpreterException("File not opened: " + fileName.value());
        }

        BufferedReader br;
        try {
            br = fileTable.getValue(fileName);
        } catch (LanguageInterpreterADTException e) {
            throw new LanguageInterpreterException(e.getMessage());
        }

        try {
            br.close();
        } catch (IOException e) {
            throw new LanguageInterpreterException("Error while closing file: " + e.getMessage());
        }

        try {
            fileTable.remove(fileName);
        } catch (LanguageInterpreterADTException e) {
            throw new LanguageInterpreterException(e.getMessage());
        }

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CloseRFileStatement(expression.deepCopy());
    }
}
