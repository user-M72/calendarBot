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
import java.util.List;

@Component
public class StartEventNotification {

    @Autowired private UserRepository userRepository;
    @Autowired private EventRepository eventRepository;
    @Autowired private TelegramBot telegramBot;


    @Scheduled(cron = "0 * * * * *")
    private void startEventNotification(){

        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
        LocalDateTime to = now.plusSeconds(59);

        for (User user: userRepository.findAll()){
            List<Event> startingEvents = eventRepository.findAllByUserAndDateTimeBetweenOrderByDateTimeAsc(user, now, to);

            for (Event event: startingEvents){
                String message = "\uD83D\uDE80 Event: " + event.getTitle() + "\n has just started";
                telegramBot.sendMessage(user.getChatId(), message);
            }
        }
    }
}
