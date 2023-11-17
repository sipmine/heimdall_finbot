package ru.sipmine.finBot.BotCommands;

public abstract class AbstractSequntCommand {
    private String commandIndif;
    private String description;

    AbstractSequntCommand(String commandIndif, String description) {
        this.commandIndif = commandIndif;
        this.description = description;
    }

    public String getCommandIdentifier() {
        return commandIndif;
    }
    
    public String getDescription() {
        return description;
    }

    public abstract void handle(String message);

    public abstract void callback(String call_data);

}
