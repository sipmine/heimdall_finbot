package ru.sipmine.finBot.botCommands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import ru.sipmine.data.services.UserService;
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

    public int getUserId(Message message, UserService userService) {
        return userService.findIdByTelegramUserName(message.getFrom().getUserName());
    }

    public long getChatId(Message message) {
        
        return message.getChatId();
    }

    public abstract SendMessage handle( Update update);

    public abstract SendMessage callback( Update update, String arg);

}
