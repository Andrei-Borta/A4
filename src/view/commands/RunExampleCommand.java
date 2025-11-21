package view.commands;

import controller.Controller;
import exceptions.LanguageInterpreterException;

public class RunExampleCommand extends Command {
    private final Controller controller;

    public RunExampleCommand(String key, String desc, Controller controller) {
        super(key, desc);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            controller.allSteps(true);
        } catch (LanguageInterpreterException e) {
            System.out.println("Runtime error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error while running example: " + e.getMessage());
        }
    }
}
