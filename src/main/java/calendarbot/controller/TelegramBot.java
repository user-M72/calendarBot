package calendarbot.controller;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final String botUserName;
    private final String botToken;

    public TelegramBot(String botUserName, String botToken) {
        this.botUserName = botUserName;
        this.botToken = botToken;
    }


    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            switch (message) {
                case "/start" -> sendMessage(chatId, "\uD83D\uDC4B Hi! I'm a calendar bot. Use /add to add an event");
                case "/add" -> sendMessage(chatId, "✍ Enter the event in the format: Title; yyyy-MM-dd HH:mm");
                case "/list" -> {
                    String event =
                }
            }
        }

    }


}
