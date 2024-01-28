package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.Buttons;
import pro.sky.telegrambot.repository.ShelterRepository;

//import static jdk.internal.joptsimple.internal.Messages.message;

@Service
public class ShelterServiceImpl implements ShelterService {
    private final TelegramBot telegramBot;
    private final ShelterRepository repository;
    private Buttons buttons;
    private final Logger logger = LoggerFactory.getLogger(ShelterServiceImpl.class);

    public ShelterServiceImpl(TelegramBot telegramBot, ShelterRepository repository, Buttons buttons) {
        this.telegramBot = telegramBot;
        this.repository = repository;
        this.buttons = buttons;
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
            sendButtonsOfStep0(chatId, " Добро пожаловать в PetShelterBot, "
                    + update.message().from().firstName() + "! Я помогаю взаимодействовать с приютами для животных!");



//                String messageText = " Выберите приют который Вас интересует:";new Buttons().
//                changeMessage(messageId, chatId, messageText, .selectionAnimalButtons());
//            ;


        }
    }

   @Override
   public void sendMessage(Long chatId, String messageText) {
        SendMessage sendMessage = new SendMessage(chatId, messageText);
        telegramBot.execute(sendMessage);
    }
    public  void sendButtonsOfStep0(Long chatId, String messageText) {
        SendMessage sendMessage = new SendMessage(chatId, messageText);
        sendMessage.replyMarkup(buttons.buttonsOfStart());
        telegramBot.execute(sendMessage);
    }

}
