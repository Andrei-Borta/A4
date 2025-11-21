package model.expression;

import exceptions.LanguageInterpreterException;
import model.adt.IDictionary;
import model.value.IValue;

public interface IExpression {
    IValue evaluate(IDictionary<String, IValue> symTable) throws LanguageInterpreterException;

    IExpression deepCopy();
}
