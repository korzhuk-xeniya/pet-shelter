package pro.sky.telegrambot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.User;
import pro.sky.telegrambot.repository.UserRepository;

import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    //Добавить нового пользователя в БД
    public User userAdd(User user) {
        logger.info("Был вызван метод для добавления нового пользователя в базу данных", user);
        return userRepository.save(user);
    }

    @Override
    //Удалить пользователя из БД
    public void deleteUser(Long id) {
        logger.info("Был вызван метод для удаления пользователя из базы данных", id);
        userRepository.deleteById(id);
    }

    @Override
    //Проверить, если пользователь в бд по его chatId
    public boolean checkIdChatUser(int chatId) {
        logger.info("Был вызван метод для поиска  пользователя в базе данных", chatId);
        return userRepository.existsByChatId(chatId);
    }

    @Override
    //Обновить пользователя в бд
    public User updateUser(User user) {
        logger.info("Был вызван метод для обновления пользователя в базе данных", user);
        userRepository.save(user);
        return user;
    }

    @Override
    //Получить юзера из бд
    public Optional<User> getUserByChatId(int chatId) {
        logger.info("Был вызван метод для получения пользователя из базы данных", chatId);
        return userRepository.findByChatId(chatId);
    }
}

