package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.model.Update;
import pro.sky.telegrambot.model.Animal;
import pro.sky.telegrambot.model.User;

import java.util.List;
import java.util.Optional;

public interface AnimalService {
    Animal add(Animal animal);

    Animal get(long id);

    Animal update(Long id, Animal animal);

    void delete(long id);

    List<Animal> allAnimals();


    /**
     * Поиск пользователя по chatId, если он есть то обновляем dateTimeToTook, если нет, создается новый пользователь
     */
    void saveUserIdInAnimal(Update update, Animal animal);


}
