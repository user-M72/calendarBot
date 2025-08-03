package calendarbot.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class BotMenu {

    @Autowired private TelegramBot telegramBot;

    @PostConstruct
    public void initCommands(){
        List<BotCommand> commandList = new ArrayList<>();
        commandList.add(new BotCommand("/start", "start the bot"));
        commandList.add(new BotCommand("/add", "Added event"));
        commandList.add(new BotCommand("/list", "Today`s event"));
        commandList.add(new BotCommand("/info", "Some info command"));
        commandList.add(new BotCommand("/help", "help"));

        try {
            telegramBot.execute(new SetMyCommands(commandList, new BotCommandScopeDefault(), null));
        }  catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
}
