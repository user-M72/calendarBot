package calendarbot.entity;

import calendarbot.entity.baseDomain.BaseDomain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity(name = "users")
@Getter
@Setter
public class User extends BaseDomain<UUID> {

    @Column(nullable = false, unique = true)
    private Long chatId;
    private String Username;
    private String phoneNumber;

}
