package com.coding_dui.cestclient.command;

public abstract class Command {
    private final String name;
    private final String description;
    private final String usage;

    public Command(String name, String description, String usage) {
        this.name = name;
        this.description = description;
        this.usage = usage;
    }

    /** Runs the command. Arguments are the whitespace-separated words after the command name. */
    public abstract void execute(String[] args);

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }
}
