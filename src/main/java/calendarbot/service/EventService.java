package calendarbot.service;

import calendarbot.entity.User;

public interface EventService {
    User createdUser(Long chatId, String username, String phoneNumber);
    User getUserByChatId(Long chatId);

    void eventFromMessage(Long chatId, String message);

    String getTodayEventsAssString(Long chatId);
}
