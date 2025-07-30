package calendarbot.service;


import calendarbot.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService{
    @Autowired
    private EventRepository eventRepository;

    @Override
    public void eventFromMessage(Long chatId, String message) {

    }

    @Override
    public String getTodayEventsAssString(Long chatId) {
        return "";
    }
}
