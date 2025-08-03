package calendarbot.service;

import calendarbot.entity.User;

import java.util.List;

public interface UserService {
    User User(Long chatId, String username, String phoneNumber);

    User getByChatId(Long chatId);

}
