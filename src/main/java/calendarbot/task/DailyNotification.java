package calendarbot.task;

import calendarbot.controller.TelegramBot;
import calendarbot.entity.User;
import calendarbot.repository.UserRepository;
import calendarbot.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DailyNotification {


    @Autowired private EventService eventService;
    @Autowired private TelegramBot telegramBot;
    @Autowired private UserRepository userRepository;

    @Scheduled(cron = "0 0 9 * * * ")
    public void sendNotification(){

        for (User user : userRepository.findAll()) {
            String events = eventService.getTodayEventsAssString(user.getChatId());
            if (!events.isEmpty()) {
                telegramBot.sendMessage(user.getChatId(), events);
            }
        }
    }
}
