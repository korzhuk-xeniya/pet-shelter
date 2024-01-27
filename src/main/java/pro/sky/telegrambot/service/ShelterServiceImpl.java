package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.repository.ShelterRepositoty;

@Service
public class ShelterServiceImpl implements ShelterService {
    private final TelegramBot telegramBot;
    private final ShelterRepositoty repository;
    private final Logger logger = LoggerFactory.getLogger(ShelterServiceImpl.class);

    public ShelterServiceImpl(TelegramBot telegramBot, ShelterRepositoty repository) {
        this.telegramBot = telegramBot;
        this.repository = repository;
    }

    @Override
    public void process(Update update) {


        if (update.message() == null) {
            logger.info("пользователь отправил пустое сообщение");
            return;
        }
        Long chatId = update.message().chat().id();
        String message = update.message().text();
        if (message == null) {
            sendMessage(chatId, "для начала работы, отправь /start");
            return;
        }
        if (message.equals("/start")) {
            sendWelcomeMessage(chatId);
        }
    }

    @Override
    public void sendMessage(Long chatId, String messageText) {
        SendMessage sendMessage = new SendMessage(chatId, messageText);
        telegramBot.execute(sendMessage);
    }

    @Override
    public void sendWelcomeMessage(Long chatId) {
        sendMessage(chatId, " Добро пожаловать в бот, я помогаю взаимодействовать с приютами для животных!");
    }
}
