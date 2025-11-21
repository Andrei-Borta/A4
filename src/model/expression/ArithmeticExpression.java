package model.expression;

import exceptions.LanguageInterpreterException;
import model.adt.IDictionary;
import model.type.IntegerType;
import model.value.IValue;
import model.value.IntegerValue;

public record ArithmeticExpression(String operator, IExpression left, IExpression right)
        implements IExpression {

    @Override
    public String toString() {
        return "(" + left.toString() + " " + operator + " " + right.toString() + ")";
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> symTable) throws LanguageInterpreterException {
        var leftTerm = left.evaluate(symTable);
        var rightTerm = right.evaluate(symTable);

        if (!(leftTerm.getType() instanceof IntegerType) || !(rightTerm.getType() instanceof IntegerType)) {
            throw new LanguageInterpreterException("Arithmetic operands must be integers");
        }

        var leftValue = (IntegerValue) leftTerm;
        var rightValue = (IntegerValue) rightTerm;

        return switch (operator) {
            case "+" -> new IntegerValue(leftValue.value() + rightValue.value());
            case "-" -> new IntegerValue(leftValue.value() - rightValue.value());
            case "*" -> new IntegerValue(leftValue.value() * rightValue.value());
            case "/" -> {
                if (rightValue.value() == 0) {
                    throw new LanguageInterpreterException("Division by zero");
                }
                yield new IntegerValue(leftValue.value() / rightValue.value());
            }
            default -> throw new LanguageInterpreterException("Invalid arithmetic operator: " + operator);
        };
    }

    @Override
    public IExpression deepCopy() {
        return new ArithmeticExpression(operator, left.deepCopy(), right.deepCopy());
    }
}
