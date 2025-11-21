package model.expression;

import exceptions.LanguageInterpreterException;
import model.adt.IDictionary;
import model.type.BooleanType;
import model.value.BooleanValue;
import model.value.IValue;

public record LogicalExpression(String operator, IExpression left, IExpression right)
        implements IExpression {

    @Override
    public String toString() {
        return "(" + left.toString() + " " + operator + " " + right.toString() + ")";
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> symTable) throws LanguageInterpreterException {
        var leftTerm = left.evaluate(symTable);
        var rightTerm = right.evaluate(symTable);

        if (!(leftTerm.getType() instanceof BooleanType) || !(rightTerm.getType() instanceof BooleanType)) {
            throw new LanguageInterpreterException("Logical operands must be booleans");
        }

        var leftValue = (BooleanValue) leftTerm;
        var rightValue = (BooleanValue) rightTerm;

        return switch (operator) {
            case "&&" -> new BooleanValue(leftValue.value() && rightValue.value());
            case "||" -> new BooleanValue(leftValue.value() || rightValue.value());
            default -> throw new LanguageInterpreterException("Invalid logical operator: " + operator);
        };
    }

    @Override
    public IExpression deepCopy() {
        return new LogicalExpression(operator, left.deepCopy(), right.deepCopy());
    }

}
