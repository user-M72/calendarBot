package calendarbot.service;

public interface EventService {

    void eventFromMessage(Long chatId, String message);

    String getTodayEventsAssString(Long chatId);
}
