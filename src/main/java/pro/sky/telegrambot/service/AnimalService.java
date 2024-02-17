package pro.sky.telegrambot.service;

import pro.sky.telegrambot.model.Animal;
import pro.sky.telegrambot.model.User;

import java.util.List;

public interface AnimalService {
    Animal add(Animal animal);

    Animal get(long id);

    Animal update(Long id, Animal animal);

    void delete(long id);

    List<Animal> allAnimals();



//    void updateUserId(User user, Animal animal);

    void updateUserId(User user, long animalId);
}
