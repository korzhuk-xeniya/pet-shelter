package pro.sky.telegrambot.service;

import pro.sky.telegrambot.model.Volunteer;

import java.util.List;
import java.util.UUID;

public interface VolunteerService {
    Volunteer saveVolunteerInBd(Volunteer volunteer);

    Volunteer readVolunteer (UUID id);

    Volunteer updateVolunteerById(Volunteer volunteer);

    void deleteById(UUID id);



    List<Volunteer> findAllVolunteers();
}
