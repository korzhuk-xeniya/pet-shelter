package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;

import java.io.IOException;

public interface ShelterService {
     void process(Update update) throws IOException;

//    BotApiMethod<>

    void sendMessage(Long chatId, String messageText);

//    void sendMessageByUserId(Long userID, String messageText);

    void sendMessageByUserId(Update update, Long userID, String messageText);

    void sendMenuButton(Long chatId, String messageText);

    void sendMenuVolunteer(Long chatId, String messageText);

    void sendButtonOfVolunteerForReports(Long chatId, String messageText);

    void sendButtonChooseAnimal(Long chatId, String messageText);

    void sendButtonsOfStep0(Long chatId, String messageText);



    void changeMessage(long chatIdInButton, String messageText);
}
