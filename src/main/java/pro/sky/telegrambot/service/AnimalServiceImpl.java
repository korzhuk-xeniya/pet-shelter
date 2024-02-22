package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Animal;
import pro.sky.telegrambot.model.User;
import pro.sky.telegrambot.repository.AnimalRepository;
import pro.sky.telegrambot.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalServiceImpl implements AnimalService {

    private final Logger logger = LoggerFactory.getLogger(AnimalServiceImpl.class);
    private final AnimalRepository animalRepository;
    private final UserRepository userRepository;
    private final UserService userService;
//    private final User user;

    public AnimalServiceImpl(AnimalRepository animalRepository, UserRepository userRepository, UserService userService) {
        this.animalRepository = animalRepository;
//        this.user = user;
        this.userRepository = userRepository;
        this.userService = userService;
    }


    /**
     * Добавление нового животного.
     */
    @Override
    public Animal add(Animal animal) {
        logger.info("Был вызван метод для добавления нового животного в базу данных", animal);
        return animalRepository.save(animal);
    }

    /**
     * Получение информации по животному через ID
     */
    @Override
    public Animal get(long id) {
        logger.info("Был вызван метод для получения животного по id", id);
        return animalRepository.findById(id).orElse(null);

    }

    /**
     * Метод обновления информации о животном.
     */
    @Override
    public Animal update(Long id, Animal animal) {
        logger.info("Был вызван метод для обновления животного в базе данных", animal);
//             создается новый объект животного.
//             передаётся ему ID существующего животного, которого необходимо отредактировать
//             передаются новые параметры для животного
        Animal savedAnimal = new Animal(id, animal.getAgeMonth(), animal.getNameOfAnimal(), animal.getPhotoLink(),
                animal.getGender(), animal.getPetType());
        return animalRepository.save(savedAnimal);
    }

    /**
     * Метод удаления животного надо доработать
     */
    @Override
    public void delete(long id) {

        // Удаляем животное по его айди
        animalRepository.deleteById(id);
//        return "Животное удалено";
    }

    /**
     * Метод выведения списка всех животных.
     */
    @Override
    public List<Animal> allAnimals() {
        return animalRepository.findAll();
    }

    @Override
    /**
     * Поиск пользователя по chatId, если он есть то добавляем к животному
     */
    public void saveUserIdInAnimal(Update update, Animal animal) {
        logger.info("Был вызван метод для усыновителя животного в базе данных",update, animal);
        int chatId = update.message().chat().id().intValue();
        Optional<User> userOptional = userService.getUserByChatId(chatId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Animal pet = new Animal(animal.getId(), animal.getAgeMonth(), animal.getNameOfAnimal(),
                    animal.getPhotoLink(), animal.getGender(), animal.getPetType(),user);
            animalRepository.save(pet);
        }
    }

}

