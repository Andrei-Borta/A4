package model.statement;

import exceptions.LanguageInterpreterException;
import model.expression.IExpression;
import model.state.ProgramState;
import model.value.BooleanValue;
import model.value.IValue;

public record IfStatement(IExpression condition, IStatement thenBranch, IStatement elseBranch) implements IStatement {
    @Override
    public String toString(){
        return "if (" + condition.toString() + ") " + "then (" + thenBranch.toString() + ") " + "else (" + elseBranch.toString() + ")";
    }

    @Override
    public IStatement deepCopy() {
        return new IfStatement(condition.deepCopy(), thenBranch.deepCopy(), elseBranch.deepCopy());
    }

    @Override
    public ProgramState execute(ProgramState state) throws LanguageInterpreterException {
        IValue result = condition.evaluate(state.symbolTable());

        if (result instanceof BooleanValue(boolean value)) {
            if (value) {
                state.executionStack().push(thenBranch);
            } else {
                state.executionStack().push(elseBranch);
            }
        } else {
            throw new LanguageInterpreterException("Condition expression does not evaluate to a boolean.");
        }
        return state;
    }


}
