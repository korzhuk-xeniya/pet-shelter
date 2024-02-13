package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.model.Update;

public interface ShelterService {
     void process(Update update);

//    BotApiMethod<>

    void sendMessage(Long chatId, String messageText);

    void sendMenuButton(Long chatId, String messageText);

    void sendMenuVolunteer(Long chatId, String messageText);

    void sendButtonChooseAnimal(Long chatId, String messageText);

    void sendButtonsOfStep0(Long chatId, String messageText);
}
