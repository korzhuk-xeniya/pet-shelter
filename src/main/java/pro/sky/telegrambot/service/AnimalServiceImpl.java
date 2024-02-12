package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Animal;
import pro.sky.telegrambot.repository.AnimalRepository;

import java.util.List;
@Service
public class AnimalServiceImpl implements AnimalService {


    private final AnimalRepository animalRepository;
//    private final Animal animal;

    public AnimalServiceImpl(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }


    /**
         * Добавление нового животного.
         */
        @Override
        public Animal add(Animal animal) {
            return animalRepository.save(animal);
        }

        /**
         * Получение информации по животному через ID
         */
        @Override
        public Animal get(long id) {
            return animalRepository.findById(id).orElse(null);

        }

        /**
         * Метод обновления ифнормации о животном.
         */
        @Override
        public Animal update(Long id, Animal animal) {
//             создается новый объект животного.
//             передаётся ему ID существующего животного, которого необходимо отредактировать
//             передаются новые параметры для животного
        Animal savedAnimal = new Animal(animal.getAgeMonth(), animal.getNameOfAnimal(), animal.getPhotoLink(),
                animal.getGender(), animal.getPetType());
            return animalRepository.save(animal);
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

    }

