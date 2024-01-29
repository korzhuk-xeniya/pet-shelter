package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.EditMessageText;
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
            return ;
        }
        Long chatId = update.message().chat().id();
        String message = update.message().text();
        Long userId = update.message().from().id();
        String userName = update.message().from().firstName();
        int messageId = update.message().messageId();

        if (message == null) {
            sendMessage(chatId, "для начала работы, отправь /start");
            return ;
        }
        if (message.equals("/start")) {
            sendMenuButton(chatId, " Добро пожаловать в PetShelterBot, "
                    + update.message().from().firstName() + "! Я помогаю взаимодействовать с приютами для животных!");
        }
        if (update.callbackQuery() != null) {
            chatId = update.callbackQuery().message().chat().id();
            userId = update.callbackQuery().from().id();
            userName = update.callbackQuery().from().firstName();
            message = update.callbackQuery().message().text();
            messageId = update.callbackQuery().message().messageId();
            String receivedMessage = update.callbackQuery().data();
            if (receivedMessage.equals("Меню")) {
//            switch (receivedMessage) {
//                //Cтартовый блок
//                case "Меню":{
                changeMessage(messageId, chatId,"Выберите запрос, который Вам подходит. " +
                        "Если ни один из вариантов не подходит, я могу позвать Волонтера!", buttons.buttonsOfStart());
//                    EditMessageText message1 = new EditMessageText(chatId, messageId, "Выберите запрос, который Вам подходит. " +
//                            "Если ни один из вариантов не подходит, я могу позвать Волонтера!");
//
//                    message1.replyMarkup(buttons.buttonsOfStart());
//                    telegramBot.execute(message1);


                }
                //  блок определения запроса
//                case "В начало" -> {
//                    sendMenuButton(chatId, message);
//                }
//                    break;
//                case "Как взять животное из приюта?" -> takeAnimalSelection(messageId, chatId);
//                case "Информация о приюте" -> shelterInformationSelection(messageId, chatId);
//                case "Позвать волонтера" -> callAVolunteer(update);
//                case "Прислать отчет о питомце" -> petReportSelection(messageId, chatId);


//                //блок “Прислать отчет о питомце”
//                case "Форма ежедневного отчета" -> {
//                    takeDailyReportFormPhoto(chatId);
//                    photoCheckButton = true; // Устанавливаем флаг в true после нажатия кнопки
//                }
//
//                //Блок "Информация о приюте"
//                case "Информация о приюте для кошек" -> aboutCatShelterSelection(messageId, chatId);
//                case "Расписание работы приюта для кошек" -> catShelterWorkingHoursSelection(messageId, chatId);
//                case "Контакты охраны приюта для кошек" -> catShelterSecurityContactSelection(messageId, chatId);
//                case "Информация о приюте для собак" -> aboutDogShelterSelection(messageId, chatId);
//                case "Расписание работы приюта для собак" -> dogShelterWorkingHoursSelection(messageId, chatId);
//                case "Контакты охраны приюта для собак" -> dogShelterSecurityContactSelection(messageId, chatId);
//                case "Общие правила поведения" -> safetyRecommendationsSelection(messageId, chatId);
//                case "Запись ваших контактов", "Запись контактов" -> recordingContactsSelection(messageId, chatId);
//
//                //блок “Как взять животное из приюта”
//                case "Правила знакомства" -> datingRulesSelection(messageId, chatId);
//                case "Список документов" -> documentsSelection(messageId, chatId);
//                case "Рекомендации по транспортировке" -> transportationSelection(messageId, chatId);
//                case "Обустройство котенка" -> KittenArrangementSelection(messageId, chatId);
//                case "Обустройство щенка" -> puppyArrangementSelection(messageId, chatId);
//                case "Обустройство для взрослого кота" -> arrangementAdultSelectionCat(messageId, chatId);
//                case "Обустройство для взрослой собаки" -> arrangementAdultSelectionDog(messageId, chatId);
//                case "Обустройство для ограниченного" -> arrangementLimitedSelection(messageId, chatId);
//                case "Cписок причин" -> listReasonsSelection(messageId, chatId);
            }

    }



    @Override
    public void sendMessage(Long chatId, String messageText) {
        SendMessage sendMessage = new SendMessage(chatId, messageText);
        telegramBot.execute(sendMessage);
    }

    @Override
    public void sendMenuButton(Long chatId, String messageText) {
        SendMessage sendMessage = new SendMessage(chatId, messageText);
        sendMessage.replyMarkup(buttons.buttonMenu());
        telegramBot.execute(sendMessage);
    }

    @Override
    public void sendButtonsOfStep0(Long chatId, String messageText) {
        SendMessage sendMessage = new SendMessage(chatId, messageText);
        sendMessage.replyMarkup(buttons.buttonsOfStart());
        telegramBot.execute(sendMessage);
    }

    /**
     * метод для создания/изменения сообщения
     */
    private void changeMessage(int messageId, long chatIdInButton, String messageText, InlineKeyboardMarkup keyboardMarkup) {
        EditMessageText editMessageText = new EditMessageText(chatIdInButton, messageId, messageText);

        editMessageText.replyMarkup(keyboardMarkup);
        telegramBot.execute(editMessageText);

    }


}
