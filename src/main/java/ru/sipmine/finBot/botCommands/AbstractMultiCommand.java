package ru.sipmine.finBot.botCommands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public abstract class AbstractMultiCommand {
    private String commandIndif;
    private String description;

    AbstractMultiCommand(String commandIndif, String description) {
        this.commandIndif = commandIndif;
        this.description = description;
    }

    public String getCommandIdentifier() {
        return commandIndif;
    }
    
    public String getDescription() {
        return description;
    }

    public abstract SendMessage handle( Update update);

    public abstract SendMessage callback( Update update, String arg);

}
