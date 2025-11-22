package model.statement;

import exceptions.LanguageInterpreterADTException;
import exceptions.LanguageInterpreterException;
import model.expression.IExpression;
import model.state.ProgramState;
import model.type.IntegerType;
import model.type.StringType;
import model.value.IValue;
import model.value.IntegerValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public record ReadFileStatement(IExpression expression, String variableName) implements IStatement {

    @Override
    public String toString() {
        return "read(" + expression + ", " + variableName + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws LanguageInterpreterException {
        var symTable = state.symbolTable();
        var fileTable = state.fileTable();
        var heap = state.heap();

        if (!symTable.isDefined(variableName)) {
            throw new LanguageInterpreterException("Variable " + variableName + " is not defined.");
        }

        IValue varValue;
        try {
            varValue = symTable.getValue(variableName);
        } catch (LanguageInterpreterADTException e) {
            throw new LanguageInterpreterException(e.getMessage());
        }

        if (!varValue.getType().equals(new IntegerType())) {
            throw new LanguageInterpreterException("Variable " + variableName + " is not of type int.");
        }

        IValue expValue;
        try {
            expValue = expression.evaluate(symTable, heap);
        } catch (LanguageInterpreterADTException e) {
            throw new LanguageInterpreterException(e.getMessage());
        }

        if (!expValue.getType().equals(new StringType())) {
            throw new LanguageInterpreterException("Expression does not evaluate to string.");
        }

        StringValue fileName = (StringValue) expValue;

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
            String line = br.readLine();
            int intValue;

            if (line == null || line.trim().isEmpty()) {
                intValue = 0;
            } else {
                intValue = Integer.parseInt(line.trim());
            }

            symTable.update(variableName, new IntegerValue(intValue));
        } catch (IOException e) {
            throw new LanguageInterpreterException(e.getMessage());
        } catch (NumberFormatException e) {
            throw new LanguageInterpreterException("File does not contain valid integer format.");
        }

        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new ReadFileStatement(expression.deepCopy(), variableName);
    }
}
