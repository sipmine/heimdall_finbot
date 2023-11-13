package ru.sipmine.finBot.BotCommands;



import java.util.Arrays;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import io.grpc.netty.shaded.io.netty.handler.logging.LogLevel;


public abstract class AbstractBotCommand implements IBotCommand{
    private String commandIndif;
    private String description;
    final Logger log = (Logger) LogManager.getLogger(getClass());
    AbstractBotCommand(String commandIndif, String description){
        this.commandIndif = commandIndif;
        this.description = description;
    }
    @Override
    public String getCommandIdentifier() {
        // TODO Auto-generated method stub
        return commandIndif;
    }
    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        log.debug(String.format(String.format("COMMAND: %s(%s)", message.getText(), Arrays.toString(strings))));
        try {
            SendMessage sendMessage = SendMessage
                    .builder()
                    .chatId(message.getChatId().toString())
                    .text(message.getText())
                    .build();
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(String.format("Command message processing error: %s", e.getMessage(), e));
        }
    }
}
