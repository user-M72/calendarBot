package calendarbot.service.Impl;

import calendarbot.entity.User;
import calendarbot.repository.UserRepository;
import calendarbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User User(Long chatId, String username, String phoneNumber) {
        User user = new User();
        user.setChatId(chatId);
        user.setUsername(username);
        user.setPhoneNumber(phoneNumber);
        return userRepository.save(user);
    }

    @Override
    public User getByChatId(Long chatId) {
        return userRepository.findByChatId(chatId);
    }
}
