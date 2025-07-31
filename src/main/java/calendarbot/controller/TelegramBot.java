package calendarbot.controller;

import calendarbot.entity.User;
import calendarbot.service.EventService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final Map<Long, User> registrationInProgress = new HashMap<>();

    @Autowired
    private EventService eventService;

    @Value("${telegram.bot.username}")
    private String botUserName;

    @Value("${telegram.bot.token}")
    private String token;

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            User existingUser = eventService.getUserByChatId(chatId);
            if (existingUser == null){
                if (!registrationInProgress.containsKey(chatId)){
                    User newUser = new User();
                    newUser.setChatId(chatId);
                    registrationInProgress.put(chatId, newUser);
                    sendMessage(chatId, "Enter your name: ");
                    return;
                }
                User user = registrationInProgress.get(chatId);

                if (user.getUsername() == null){
                    user.setUsername(message);
                    sendMessage(chatId, "Enter your phone number: ");
                    return;
                }
                if (user.getPhoneNumber() == null){
                    user.setPhoneNumber(message);
                    eventService.createdUser(user.getChatId(), user.getUsername(), user.getPhoneNumber());
                    registrationInProgress.remove(chatId);
                    sendMessage(chatId, "You have registered successfully!");
                    return;
                }
            }
            switch (message) {
                case "/start" -> sendMessage(chatId, "\uD83D\uDC4B Hi! I'm a calendar bot. Use /add to add an event");
                case "/add" -> sendMessage(chatId, "✍ Enter the event in the format: Title yyyy-MM-dd HH:mm");
                case "/list" -> {
                    String event = eventService.getTodayEventsAssString(chatId);
                    sendMessage(chatId, event.isEmpty() ? "\uD83D\uDCED There are no events today" : event);
                }
                default -> {
                    if (message.contains(":")){
                        try {
                            eventService.eventFromMessage(chatId, message);
                            sendMessage(chatId, "Event added!");
                        } catch (Exception e){
                            sendMessage(chatId, "Error adding event");
                        }
                    } else {
                        sendMessage(chatId, "Unknown command");
                    }
                }
            }
        }

    }

    private void sendMessage(Long chatId, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        }  catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void initCommands(){
        List<BotCommand> commandList = new ArrayList<>();
        commandList.add(new BotCommand("/start", "Launch bot"));
        commandList.add(new BotCommand("/add", "Added event"));
        commandList.add(new BotCommand("/list", "Today`s event"));
        commandList.add(new BotCommand("/help", "help"));

        try {
            this.execute(new SetMyCommands(commandList, new BotCommandScopeDefault(), null));
        }  catch (TelegramApiException e){
            e.printStackTrace();
        }
    }



}
