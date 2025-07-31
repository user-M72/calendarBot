package calendarbot.repository;

import calendarbot.entity.Event;
import calendarbot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {

    List<Event> findAllByUserAndDateTimeBetweenOrderByDateTimeAsc(User user, LocalDateTime now, LocalDateTime endOfDay);
}
