package pro.sky.telegrambot.service;

import pro.sky.telegrambot.model.User;
import pro.sky.telegrambot.repository.UserRepository;

import java.util.Optional;

public interface UserService {





    //Добавить нового пользователя в БД
    User userAdd(User user);

    //Удалить пользователя из БД
    void deleteUser(Long id);

    //Проверить, если пользователь в бд по его chatId
    boolean checkIdChatUser(int chatId);

    //Обновить пользователя в бд
    User updateUser(User user);

    //Получить юзера из бд
    Optional<User> getUserByChatId(int chatId);
}
