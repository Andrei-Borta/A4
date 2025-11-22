import controller.Controller;
import model.expression.*;
import model.state.*;
import model.statement.*;
import model.type.BooleanType;
import model.type.IntegerType;
import model.type.ReferenceType;
import model.type.StringType;
import model.value.BooleanValue;
import model.value.IValue;
import model.value.IntegerValue;
import model.value.StringValue;
import repository.IRepository;
import repository.Repository;
import view.TextMenu;
import view.commands.ExitCommand;
import view.commands.RunExampleCommand;

public class Interpreter {
    public static void main(String[] args) {

        /* Program #1
         * int v
         * v = 2
         * print(v)
         */
        IStatement ex1 = new CompoundStatement(
                new VariableDeclarationStatement(new IntegerType(), "v"),
                new CompoundStatement(
                        new AssignmentStatement(new ConstantExpression(new IntegerValue(2)), "v"),
                        new PrintStatement(new VariableExpression("v"))
                )
        );

        /* Program #2
         * int a
         * int b
         * a=2+3*5
         * b=a+1
         * print(b)
         */
        IStatement ex2 = new CompoundStatement(
                new VariableDeclarationStatement(new IntegerType(), "a"),
                new CompoundStatement(
                        new VariableDeclarationStatement(new IntegerType(), "b"),
                        new CompoundStatement(
                                new AssignmentStatement(new ArithmeticExpression("+",
                                        new ConstantExpression(new IntegerValue(2)),
                                        new ArithmeticExpression("*",
                                                new ConstantExpression(new IntegerValue(3)),
                                                new ConstantExpression(new IntegerValue(5))
                                        )
                                ), "a"),
                                new CompoundStatement(
                                        new AssignmentStatement(new ArithmeticExpression("+",
                                                new VariableExpression("a"),
                                                new ConstantExpression(new IntegerValue(1))
                                        ), "b"),
                                        new PrintStatement(new VariableExpression("b"))
                                )
                        )
                )
        );

        /* Program #3
         * bool a
         * int v
         * a = true
         * if a then v=2
         * else v=3
         * print(v)
         */
        IStatement ex3 = new CompoundStatement(
                new VariableDeclarationStatement(new BooleanType(), "a"),
                new CompoundStatement(
                        new VariableDeclarationStatement(new IntegerType(), "v"),
                        new CompoundStatement(
                                new AssignmentStatement(new ConstantExpression(new BooleanValue(true)), "a"),
                                new CompoundStatement(
                                        new IfStatement(
                                                new VariableExpression("a"),
                                                new AssignmentStatement(new ConstantExpression(new IntegerValue(2)), "v"),
                                                new AssignmentStatement(new ConstantExpression(new IntegerValue(3)), "v")
                                        ),
                                        new PrintStatement(new VariableExpression("v"))
                                )
                        )
                )
        );

        /*
         * string s;
         * s="test.in";
         * openRFile(s);
         * int a;
         * readFile(s,a);print(a);
         * readFile(s,a);print(a)
         * closeRFile(s)
         */

        IStatement ex4 = new CompoundStatement(
                new VariableDeclarationStatement(new StringType(), "s"),
                new CompoundStatement(
                        new AssignmentStatement(new ConstantExpression(new StringValue("test.in")), "s"),
                        new CompoundStatement(
                                new OpenRFileStatement(new VariableExpression("s")),
                                new CompoundStatement(
                                        new VariableDeclarationStatement(new IntegerType(), "a"),
                                        new CompoundStatement(
                                                new ReadFileStatement(new VariableExpression("s"), "a"),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("a")),
                                                        new CompoundStatement(
                                                                new ReadFileStatement(new VariableExpression("s"), "a"),
                                                                new CompoundStatement(
                                                                        new PrintStatement(new VariableExpression("a")),
                                                                        new CloseRFileStatement(new VariableExpression("s"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        /*
        * int a
        * int b
        * int c
        * a = 3
        * b = 5
        * if a < b
        * then
        * c = 1
        * else
        * c = 2
        * print c
        */
        IStatement ex5 = new CompoundStatement(
                new VariableDeclarationStatement(new IntegerType(), "a"),
                new CompoundStatement(
                        new VariableDeclarationStatement(new IntegerType(), "b"),
                        new CompoundStatement(
                                new VariableDeclarationStatement(new IntegerType(), "c"),
                                new CompoundStatement(
                                        new AssignmentStatement(new ConstantExpression(new IntegerValue(3)), "a"),
                                        new CompoundStatement(
                                                new AssignmentStatement(new ConstantExpression(new IntegerValue(5)), "b"),
                                                new CompoundStatement(
                                                        new IfStatement(new RelationalExpression("<", new VariableExpression("a"), new VariableExpression("b")),
                                                                    new AssignmentStatement(new ConstantExpression(new IntegerValue(1)), "c"),
                                                                    new AssignmentStatement(new ConstantExpression(new IntegerValue(2)), "c")
                                                                ),
                                                        new PrintStatement(new VariableExpression("c"))
                                                )
                                        )
                                )
                        )
                )
        );

        // Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        IStatement ex6 = new CompoundStatement(
                new VariableDeclarationStatement(new ReferenceType(new IntegerType()), "v"),
                new CompoundStatement(
                        new HeapAllocationStatement("v", new ConstantExpression(new IntegerValue(20))),
                        new CompoundStatement(
                                new PrintStatement(new HeapReadingExpression(new VariableExpression("v"))),
                                new CompoundStatement(
                                        new HeapWritingStatement("v", new ConstantExpression(new IntegerValue(30))),
                                        new PrintStatement(new ArithmeticExpression(
                                                "+",
                                                new HeapReadingExpression(new VariableExpression("v")),
                                                new ConstantExpression(new IntegerValue(5))
                                        ))
                                )
                        )
                )
        );

        // Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
        IStatement ex7 = new CompoundStatement(
                new VariableDeclarationStatement(new ReferenceType(new IntegerType()), "v"),
                new CompoundStatement(
                        new HeapAllocationStatement("v", new ConstantExpression(new IntegerValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement(new ReferenceType(new ReferenceType(new IntegerType())), "a"),
                                new CompoundStatement(
                                        new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new HeapAllocationStatement("v", new ConstantExpression(new IntegerValue(30))),
                                                new CompoundStatement(
                                                        new PrintStatement(new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("a")))),
                                                        new CompoundStatement(
                                                                new HeapWritingStatement("a", new VariableExpression("v")),
                                                                new PrintStatement(new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("a"))))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        // int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        IStatement ex8 =  new CompoundStatement(
                new VariableDeclarationStatement(new IntegerType(), "v"),
                new CompoundStatement(
                        new AssignmentStatement(new ConstantExpression(new IntegerValue(4)), "v"),
                        new CompoundStatement(
                                new WhileStatement(
                                        new RelationalExpression(">", new VariableExpression("v"), new ConstantExpression(new IntegerValue(0))),
                                        new CompoundStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new AssignmentStatement(new ArithmeticExpression("-", new VariableExpression("v"), new ConstantExpression(new IntegerValue(1))), "v")
                                        )
                                ),
                                new PrintStatement(new VariableExpression("v"))
                        )
                )
        );

        // =========================================================================================

        ProgramState prg1 = new ProgramState(new ExecutionStack<IStatement>(), new SymbolTable<String, IValue>(), new FileTable(), new Out<IValue>(), new Heap(), ex1.deepCopy());
        ProgramState prg2 = new ProgramState(new ExecutionStack<IStatement>(), new SymbolTable<String, IValue>(), new FileTable(), new Out<IValue>(), new Heap(), ex2.deepCopy());
        ProgramState prg3 = new ProgramState(new ExecutionStack<IStatement>(), new SymbolTable<String, IValue>(), new FileTable(), new Out<IValue>(), new Heap(), ex3.deepCopy());
        ProgramState prg4 = new ProgramState(new ExecutionStack<IStatement>(), new SymbolTable<String, IValue>(), new FileTable(), new Out<IValue>(), new Heap(), ex4.deepCopy());
        ProgramState prg5 = new ProgramState(new ExecutionStack<IStatement>(), new SymbolTable<String, IValue>(), new FileTable(), new Out<IValue>(), new Heap(), ex5.deepCopy());
        ProgramState prg6 = new ProgramState(new ExecutionStack<IStatement>(), new SymbolTable<String, IValue>(), new FileTable(), new Out<IValue>(), new Heap(), ex6.deepCopy());
        ProgramState prg7 = new ProgramState(new ExecutionStack<IStatement>(), new SymbolTable<String, IValue>(), new FileTable(), new Out<IValue>(), new Heap(), ex7.deepCopy());
        ProgramState prg8 = new ProgramState(new ExecutionStack<IStatement>(), new SymbolTable<String, IValue>(), new FileTable(), new Out<IValue>(), new Heap(), ex8.deepCopy());

        IRepository repo1 = new Repository(prg1, "log1.txt");
        Controller ctr1 = new Controller(repo1);

        IRepository repo2 = new Repository(prg2, "log2.txt");
        Controller ctr2 = new Controller(repo2);

        IRepository repo3 = new Repository(prg3, "log3.txt");
        Controller ctr3 = new Controller(repo3);

        IRepository repo4 = new Repository(prg4, "log4.txt");
        Controller ctr4 = new Controller(repo4);

        IRepository repo5 = new Repository(prg5, "log5.txt");
        Controller ctr5 = new Controller(repo5);

        IRepository repo6 = new Repository(prg6, "log6.txt");
        Controller ctr6 = new Controller(repo6);

        IRepository repo7 = new Repository(prg7, "log7.txt");
        Controller ctr7 = new Controller(repo7);

        IRepository repo8 = new Repository(prg8, "log8.txt");
        Controller ctr8 = new Controller(repo8);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "Exit"));
        menu.addCommand(new RunExampleCommand("1", ex1 == null ? "Example 1" : ex1.toString(), ctr1));
        menu.addCommand(new RunExampleCommand("2", ex2 == null ? "Example 2" : ex2.toString(), ctr2));
        menu.addCommand(new RunExampleCommand("3", ex3 == null ? "Example 3" : ex3.toString(), ctr3));
        menu.addCommand(new RunExampleCommand("4", ex4 == null ? "Example 4" : ex4.toString(), ctr4));
        menu.addCommand(new RunExampleCommand("5", ex5 == null ? "Example 5" : ex5.toString(), ctr5));
        menu.addCommand(new RunExampleCommand("6", ex6 == null ? "Example 6" : ex6.toString(), ctr6));
        menu.addCommand(new RunExampleCommand("7", ex7 == null ? "Example 7" : ex7.toString(), ctr7));
        menu.addCommand(new RunExampleCommand("8", ex8 == null ? "Example 8" : ex8.toString(), ctr8));
        menu.show();
    }
}
