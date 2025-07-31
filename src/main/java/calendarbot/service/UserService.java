package calendarbot.service;

import calendarbot.dto.UserResponseDto;
import calendarbot.entity.User;

import java.util.List;

public interface UserService {
    List<User> get();
}
