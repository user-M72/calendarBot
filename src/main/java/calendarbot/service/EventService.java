package calendarbot.service;

import calendarbot.entity.User;

public interface EventService {

    void eventFromMessage(Long chatId, String message);

    String getTodayEventsAssString(Long chatId);
}
