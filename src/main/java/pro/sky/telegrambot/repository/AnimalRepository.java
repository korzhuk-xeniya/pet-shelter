package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.Animal;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
}
