package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.Animal;
import pro.sky.telegrambot.model.User;

import java.util.Optional;

public interface AnimalRepository extends JpaRepository<Animal, Long> {


    Optional<Animal> findAnimalById(long id);
}
