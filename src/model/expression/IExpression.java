package model.expression;

import exceptions.LanguageInterpreterException;
import model.adt.IDictionary;
import model.adt.IHeap;
import model.value.IValue;

public interface IExpression {
    IValue evaluate(IDictionary<String, IValue> symTable, IHeap<Integer, IValue> heap) throws LanguageInterpreterException;

    IExpression deepCopy();
}
