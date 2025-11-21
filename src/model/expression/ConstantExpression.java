package model.expression;

import exceptions.LanguageInterpreterException;
import model.adt.IDictionary;
import model.value.IValue;

public record ConstantExpression(IValue value) implements IExpression {
    @Override
    public String toString()
    {
        return value.toString();
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> symTable) throws LanguageInterpreterException {
        return value;
    }

    @Override
    public IExpression deepCopy() {
        return new ConstantExpression(value.deepCopy());
    }

}
