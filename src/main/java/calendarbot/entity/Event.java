package calendarbot.entity;


import calendarbot.entity.baseDomain.BaseDomain;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity(name = "events")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event extends BaseDomain<UUID> {

    private String title;
    private String description;
    private String dateTime;
    private String reminderTime;

    private String UserId;
}
