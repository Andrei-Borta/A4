package model.expression;

import exceptions.LanguageInterpreterException;
import model.adt.IDictionary;
import model.type.IntegerType;
import model.value.BooleanValue;
import model.value.IValue;
import model.value.IntegerValue;

public record RelationalExpression(String operator, IExpression left, IExpression right)
        implements IExpression {

    @Override
    public String toString() {
        return "(" + left.toString() + " " + operator + " " + right.toString() + ")";
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> symTable) throws LanguageInterpreterException {
        var leftTerm = left.evaluate(symTable);
        var rightTerm = right.evaluate(symTable);

        // Check both are integers
        if (!(leftTerm.getType() instanceof IntegerType) || !(rightTerm.getType() instanceof IntegerType)) {
            throw new LanguageInterpreterException("Relational operands must be integers");
        }

        var leftValue = (IntegerValue) leftTerm;
        var rightValue = (IntegerValue) rightTerm;

        int leftInt = leftValue.value();
        int rightInt = rightValue.value();

        return switch (operator) {
            case "<"  -> new BooleanValue(leftInt < rightInt);
            case "<=" -> new BooleanValue(leftInt <= rightInt);
            case "==" -> new BooleanValue(leftInt == rightInt);
            case "!=" -> new BooleanValue(leftInt != rightInt);
            case ">"  -> new BooleanValue(leftInt > rightInt);
            case ">=" -> new BooleanValue(leftInt >= rightInt);
            default   -> throw new LanguageInterpreterException("Invalid relational operator: " + operator);
        };
    }

    @Override
    public IExpression deepCopy() {
        return new RelationalExpression(operator, left.deepCopy(), right.deepCopy());
    }
}
