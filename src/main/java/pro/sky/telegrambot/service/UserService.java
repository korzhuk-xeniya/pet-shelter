package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.model.Update;
import pro.sky.telegrambot.model.User;

import java.util.Optional;

public interface UserService {





    //Добавить нового пользователя в БД
    User userAdd(User user);

    //Удалить пользователя из БД
    void deleteUser(Long id);

    //Проверить, если пользователь в бд по его chatId
    boolean checkIdChatUser(long chatId);

    //Обновить пользователя в бд
    User updateUser(User user);

    //Получить юзера из бд
    Optional<User> getUserByChatId(long chatId);

    /**
         * Поиск пользователя по chatId, если он есть то обновляем dateTimeToTook, если нет, создается новый пользователь
         */
    void saveUser(Update update, boolean tookAPET);
}
