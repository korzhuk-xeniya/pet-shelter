package pro.sky.telegrambot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.exception.VolunteerNotFoundException;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.repository.VolunteerRepository;

import java.util.List;
import java.util.UUID;

@Service
public class VolunteerServiceImpl implements VolunteerService {
    private final VolunteerRepository volunteerRepository;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public VolunteerServiceImpl(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    /**
     * @param volunteer
     * @return волонтера, который был добавлен в базу данных
     *
     * добавление нового волонтера
     */
    @Override
    public Volunteer saveVolunteerInBd(Volunteer volunteer) {
        logger.info("Был вызван метод для добавления нового волонтера в базу данных", volunteer);

        return volunteerRepository.save(volunteer);
    }

    /**
     * @param id
     * @return волонтера по id
     */
    @Override
    public Volunteer readVolunteer (UUID id) {
        logger.error("Был вызван метод для выбрасывания ошибки если волонтер не найден по id", id);
        return volunteerRepository.findById(id).
                orElseThrow(() -> new VolunteerNotFoundException("Волонтер с id " + id + " не найден в базе данных"));
    }

    /**
     * @param volunteer
     * @return волонтера с обновленными данными
     */
    @Override
    public Volunteer updateVolunteerById(Volunteer volunteer) {
        logger.info("Был вызван метод для обновления волонтера", volunteer);
        readVolunteer(volunteer.getId());
        return volunteerRepository.save(volunteer);
    }

    /**
     * @param id
     *
     * удаление волонтера из базы данных
     */
    @Override
    public void deleteById(UUID id) {
        logger.info("Был вызван метод для удаления волонтера", id);
        volunteerRepository.deleteById(id);
    }


    /**
     * @return список всех волонтеров
     *
     * ищет всех волонтеров
     */
    @Override

    public List<Volunteer> findAllVolunteers() {
        logger.info("Был вызван метод для поиска всех волонтеров");
        return volunteerRepository.findAll().stream().toList();
    }
}
