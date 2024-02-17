package pro.sky.telegrambot.repository;

import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.events.Event;
import pro.sky.telegrambot.model.Report;
import pro.sky.telegrambot.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByChatId(long chatId);
    boolean existsByChatId(long chatId);
    Optional<User> findById(long id);
    @Transactional
    @Modifying
    @Query("UPDATE User u set u.number = ?2 where u.chatId = ?1")
    int updateNumber(long chatId, String num);

}

