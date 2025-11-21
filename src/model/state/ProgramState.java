package model.state;

import model.adt.IDictionary;
import model.adt.IFileTable;
import model.adt.IList;
import model.adt.IStack;
import model.statement.IStatement;
import model.value.IValue;

import java.util.ArrayList;
import java.util.List;

public record ProgramState(IStack<IStatement> executionStack, IDictionary<String, IValue> symbolTable, IFileTable fileTable, IList<IValue> out, IStatement startingStatement) {

    public ProgramState {
        if (startingStatement != null) {
            executionStack.push(startingStatement);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String header = "PROGRAM STATE";

        var stack = executionStack().getAll();
        String stackLine = (stack == null || stack.isEmpty())
                ? "(empty)"
                : String.join(" -> ", stack.stream()
                .map(Object::toString)
                .toArray(String[]::new));

        var symEntries = symbolTable().getAll().entrySet();
        List<String> symLines = new ArrayList<>();
        if (symEntries.isEmpty()) {
            symLines.add("(empty)");
        } else {
            int maxKey = symEntries.stream()
                    .mapToInt(e -> e.getKey().length())
                    .max().orElse(0);
            for (var e : symEntries) {
                symLines.add(String.format("%-" + maxKey + "s = %s",
                        e.getKey(), String.valueOf(e.getValue())));
            }
        }

        var fileEntries = fileTable().getAll().entrySet();
        List<String> fileLines = new ArrayList<>();
        if (fileEntries.isEmpty()) {
            fileLines.add("(empty)");
        } else {
            for (var e : fileEntries) {
                fileLines.add(e.getKey().toString());
            }
        }

        var outputs = out().getAll();
        List<String> outLines = new ArrayList<>();
        if (outputs == null || outputs.isEmpty()) {
            outLines.add("(empty)");
        } else {
            outputs.forEach(v -> outLines.add(String.valueOf(v)));
        }

        List<String> allContent = new ArrayList<>();
        allContent.add(header);
        allContent.add("Execution Stack: " + stackLine);
        allContent.add("Symbol Table:");
        allContent.addAll(symLines);
        allContent.add("File Table:");
        allContent.addAll(fileLines);
        allContent.add("Output:");
        allContent.addAll(outLines);

        int innerWidth = allContent.stream().mapToInt(String::length).max().orElse(header.length());
        int finalInnerWidth = innerWidth + 2;

        java.util.function.Function<String, String> padLine = (line) -> {
            int spaces = finalInnerWidth - line.length();
            return " " + line + " ".repeat(Math.max(0, spaces - 1));
        };

        sb.append("┌").append("─".repeat(finalInnerWidth)).append("┐\n");
        sb.append("│").append(padLine.apply(header)).append("│\n");
        sb.append("├").append("─".repeat(finalInnerWidth)).append("┤\n");

        sb.append("│").append(padLine.apply("Execution Stack: " + stackLine)).append("│\n");
        sb.append("├").append("─".repeat(finalInnerWidth)).append("┤\n");

        sb.append("│").append(padLine.apply("Symbol Table:")).append("│\n");
        for (String sl : symLines)
            sb.append("│").append(padLine.apply(sl)).append("│\n");
        sb.append("├").append("─".repeat(finalInnerWidth)).append("┤\n");

        sb.append("│").append(padLine.apply("File Table:")).append("│\n");
        for (String fl : fileLines)
            sb.append("│").append(padLine.apply(fl)).append("│\n");
        sb.append("├").append("─".repeat(finalInnerWidth)).append("┤\n");

        sb.append("│").append(padLine.apply("Output:")).append("│\n");
        for (String ol : outLines)
            sb.append("│").append(padLine.apply(ol)).append("│\n");

        sb.append("└").append("─".repeat(finalInnerWidth)).append("┘\n");
        return sb.toString();
    }

    public void pushStatement(IStatement statement) {
        if(statement != null) {
            executionStack.push(statement);
        }
    }

    public void clearOut() {
        out.clear();
    }

    public void clearSymbolTable() { symbolTable.clear(); }

    public void clearFileTable() { fileTable.clear(); }
}
