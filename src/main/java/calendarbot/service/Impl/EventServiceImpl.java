package calendarbot.service.Impl;


import calendarbot.entity.Event;
import calendarbot.entity.User;
import calendarbot.repository.EventRepository;
import calendarbot.repository.UserRepository;
import calendarbot.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void eventFromMessage(Long chatId, String message) {
        User user = userRepository.findByChatId(chatId);


        String[] parts =  message.split("-", 2);
        if (parts.length < 2) throw new IllegalArgumentException("Invalid format");

        String title = parts[0].trim();
        String dateTimePart = parts[1].trim();

        int year = LocalDateTime.now().getYear();
        String fullDateTime = year + "-" + dateTimePart;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(fullDateTime, formatter);

        Event event = new Event();
        event.setTitle(title);
        event.setDateTime(dateTime);
        event.setUser(user);

        eventRepository.save(event);
    }

    @Override
    public String getTodayEventsAssString(Long chatId) {
        User user = userRepository.findByChatId(chatId);
        if (user == null){
            return "User not found";
        }

        LocalDateTime startOfDay = LocalDateTime.now();
        LocalDateTime endOfDay = startOfDay.plusDays(1).toLocalDate().atStartOfDay();

        List<Event> events = eventRepository.findAllByUserAndDateTimeBetweenOrderByDateTimeAsc(user, startOfDay, endOfDay);

        if (events.isEmpty()){
            return "";
        }

        StringBuilder builder =new StringBuilder("\uD83D\uDCC5 Today's events:\n");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        for (Event event: events){
            builder.append("⏰ ").append(dateTimeFormatter.format(event.getDateTime()))
                    .append(" - ").append(event.getTitle()).append("\n");
        }
        return builder.toString();
    }
}
