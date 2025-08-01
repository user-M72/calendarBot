package calendarbot.task;

import calendarbot.controller.TelegramBot;
import calendarbot.entity.Event;
import calendarbot.entity.User;
import calendarbot.repository.EventRepository;
import calendarbot.repository.UserRepository;
import calendarbot.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class Notification {
    @Autowired
    private EventService eventService;
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    private UserRepository userRepository;

    @Scheduled(cron = "0 0 9 * * * ")
    public void sendNotification(){

        for (User user : userRepository.findAll()) {
            String events = eventService.getTodayEventsAssString(user.getChatId());
            if (!events.isEmpty()) {
                telegramBot.sendMessage(user.getChatId(), events);
            }
        }

    }

    @Scheduled(cron = "0 * * * * *")
    public void sendNotificationEachHalfHour() {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime targetTime = now.plusMinutes(30);

        for (User user : userRepository.findAll()) {
            List<Event> upcomingEvents = eventRepository
                    .findAllByUserAndDateTimeBetweenOrderByDateTimeAsc(user, targetTime, targetTime.plusMinutes(1));

            for (Event event : upcomingEvents) {
                String time = event.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm"));
                String message = "⏰ Reminder: Event " + event.getTitle() + " starts in 30 minutes (at " + time + ")";
                telegramBot.sendMessage(user.getChatId(), message);
            }
        }
    }
}
