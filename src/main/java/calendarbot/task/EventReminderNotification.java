package calendarbot.task;

import calendarbot.controller.TelegramBot;
import calendarbot.entity.Event;
import calendarbot.entity.User;
import calendarbot.repository.EventRepository;
import calendarbot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class EventReminderNotification {

    @Autowired private UserRepository userRepository;
    @Autowired private EventRepository eventRepository;
    @Autowired private TelegramBot telegramBot;

    @Scheduled(cron = "0 * * * * *")
    private void EventReminder(){
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
