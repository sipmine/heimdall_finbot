/**
 * This abstract class represents a bot command that can be executed by a Telegram bot.
 * It implements the IBotCommand interface and provides basic functionality for processing a message.
 * Subclasses must implement the constructor and can override the processMessage method to provide custom behavior.
 */
package ru.sipmine.finBot.botCommands;



import java.util.Arrays;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ru.sipmine.data.services.UserService;
import ru.sipmine.finBot.Keyboards.RoutesKeyboard;


public abstract class AbstractBotCommand implements IBotCommand {
    private String commandIndif;
    private String description;
    final Logger log = (Logger) LogManager.getLogger(getClass());

    AbstractBotCommand(String commandIndif, String description) {
        this.commandIndif = commandIndif;
        this.description = description;
    }

    @Override
    public String getCommandIdentifier() {
        return commandIndif;
    }

    @Override
    public String getDescription() {
        return description;
    }
    public int getUserId(Message message, UserService userService) {
        
        return userService.findIdByTelegramUserName(message.getFrom().getUserName());
    }

    public long getChatId(Message message) {   
        return message.getChatId();
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        log.debug(String.format(
                String.format("COMMAND: %s(%s)", message.getText(), Arrays.toString(strings))));
        try {
            SendMessage sendMessage = SendMessage.builder().chatId(message.getChatId().toString())
                    .text(message.getText()).replyMarkup(RoutesKeyboard.getKeyboardMarkup()).build();
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(String.format("Command message processing error: %s", e.getMessage(), e));
        }
    }
}
