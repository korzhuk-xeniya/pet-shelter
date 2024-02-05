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
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.repository.ShelterRepository;
import pro.sky.telegrambot.repository.VolunteerRepository;

import java.util.List;




@Service
public class ShelterServiceImpl implements ShelterService {
    private final TelegramBot telegramBot;
    private final ShelterRepository repository;
    private final VolunteerRepository volunteerRepository;
    private final Buttons buttons;
    private final UserService userService;
    private final VolunteerService volunteerService;
    private final Logger logger = LoggerFactory.getLogger(ShelterServiceImpl.class);
    private final List<String> admins;

    public ShelterServiceImpl(TelegramBot telegramBot, ShelterRepository repository, VolunteerRepository volunteerRepository, Buttons buttons, UserService userService, VolunteerService volunteerService, List<String> admins) {
        this.telegramBot = telegramBot;
        this.repository = repository;
        this.volunteerRepository = volunteerRepository;
        this.buttons = buttons;
        this.userService = userService;
        this.volunteerService = volunteerService;
        this.admins = admins;
    }

    @Override
    public void process(Update update) {


        if (update.message() == null && update.callbackQuery() == null) {
            logger.info("пользователь отправил пустое сообщение");
            return;
        }
//
        if (update.callbackQuery() == null) {
            Long chatId = update.message().chat().id();
            String message = update.message().text();
            Long userId = update.message().from().id();
            String userName = update.message().from().firstName();
            String userLastName = update.message().from().lastName();
            int messageId = update.message().messageId();
            if (!update.message().text().equals("/start")) {
                logger.info("пользователь отправил  сообщение с неопределенным содержанием");
                sendMessage(chatId, "для начала работы, отправь /start");
                return;
            }
            if (update.message().text().equals("/start")) {
                logger.info("пользователь отправил /start");
                sendMenuButton(chatId, " Добро пожаловать в PetShelterBot, "
                        + update.message().from().firstName() + "! Я помогаю взаимодействовать с приютами для животных!");
                if(admins.contains(update.message().from().username())) {
                    volunteerService.saveVolunteerInBd(new Volunteer(userName, userLastName, chatId));
                } else {
                    userService.saveUser(update, false);
                }
            }
        } else {
            if (update.callbackQuery() != null) {
                logger.info("пользователь нажал на кнопку");
                Long chatId = update.callbackQuery().message().chat().id();
                Long userId = update.callbackQuery().from().id();
                String userName = update.callbackQuery().from().firstName();
                String message = update.callbackQuery().message().text();
                int messageId = update.callbackQuery().message().messageId();
                String receivedMessage = update.callbackQuery().data();

//                if (receivedMessage.equals("Меню")) {
//
//                }
                switch (receivedMessage) {
                    //Cтартовый блок
                    case "Меню" -> changeMessage(messageId, chatId, "Выберите запрос, который Вам подходит. " +
                            "Если ни один из вариантов не подходит, я могу позвать Волонтера!", buttons.buttonsOfStart());

                    //  блок определения запроса
                    case "Информация о приюте" ->
                            changeMessage(messageId, chatId, "Добро пожаловать в наш приют для собак!",
                                    buttons.buttonsInformationAboutShelter());
                    case "В начало" -> changeMessage(messageId, chatId, "Вы вернулись в начало!", buttons.buttonMenu());

//                    break;
                    case "Как взять животное из приюта?" ->
                            changeMessage(messageId, chatId, "Вы вернулись в начало!", buttons.takeAnimalButton());


                case "Позвать волонтера" -> callAVolunteer(update);
//                case "Прислать отчет о питомце" -> petReportSelection(messageId, chatId);

//Блок "Информация о приюте"
//
//                case "Информация о приюте для собак" -> aboutDogShelterSelection(messageId, chatId);
//                case "Расписание работы приюта для собак" -> dogShelterWorkingHoursSelection(messageId, chatId);
//                case "Контакты охраны приюта для собак" -> dogShelterSecurityContactSelection(messageId, chatId);
//                case "Общие правила поведения" -> safetyRecommendationsSelection(messageId, chatId);
//                case "Запись ваших контактов", "Запись контактов" -> recordingContactsSelection(messageId, chatId);
//
//
// блок “Прислать отчет о питомце”
//                case "Форма ежедневного отчета" -> {
//                    takeDailyReportFormPhoto(chatId);
//                    photoCheckButton = true; // Устанавливаем флаг в true после нажатия кнопки
//                }
//
//
//
//                //блок “Как взять животное из приюта”
//                case "Правила знакомства" -> datingRulesSelection(messageId, chatId);
//                case "Список документов" -> documentsSelection(messageId, chatId);
//                case "Рекомендации по транспортировке" -> transportationSelection(messageId, chatId);
//
//                case "Обустройство щенка" -> puppyArrangementSelection(messageId, chatId);
//
//                case "Обустройство для взрослой собаки" -> arrangementAdultSelectionDog(messageId, chatId);
//                case "Обустройство для ограниченного" -> arrangementLimitedSelection(messageId, chatId);
//                case "Cписок причин" -> listReasonsSelection(messageId, chatId);
                }
            }

        }

    }

    @Override
    public void sendMessage(Long chatId, String messageText) {
        logger.info("Был вызван метод для отправки сообщения", chatId, messageText);
        SendMessage sendMessage = new SendMessage(chatId, messageText);
        telegramBot.execute(sendMessage);
    }

    /**
     * метод для отправки кнопки "Меню"
     */
    @Override
    public void sendMenuButton(Long chatId, String messageText) {
        logger.info("Был вызван метод для отправки кнопки Меню", chatId, messageText);
        SendMessage sendMessage = new SendMessage(chatId, messageText);
        sendMessage.replyMarkup(buttons.buttonMenu());
        telegramBot.execute(sendMessage);
    }

    /**
     * метод для отправки кнопок Этапа 0. Определение запроса
     */
    @Override
    public void sendButtonsOfStep0(Long chatId, String messageText) {
        logger.info("Был вызван метод для отправки кнопок 0 этапа", chatId, messageText);
        SendMessage sendMessage = new SendMessage(chatId, messageText);
        sendMessage.replyMarkup(buttons.buttonsOfStart());
        telegramBot.execute(sendMessage);
    }

    /**
     * метод для изменения сообщения
     */
    private void changeMessage(int messageId, long chatIdInButton, String messageText, InlineKeyboardMarkup keyboardMarkup) {
        logger.info("Был вызван метод для изменения сообщения", messageId, chatIdInButton, messageText, keyboardMarkup);
        EditMessageText editMessageText = new EditMessageText(chatIdInButton, messageId, messageText);

        editMessageText.replyMarkup(keyboardMarkup);

        telegramBot.execute(editMessageText);

    }


    /**
     * Провека ввода /start
     */
    private boolean isStartEntered(Update update) {
        return update.message().text() != null && update.message().text().equals("/start");
    }

    /**
     * @param update
     * Реализация кнопки "Позвать волонтера"
     */
    public void callAVolunteer(Update update) {
        List<Volunteer> volunteerList = volunteerRepository.findAll();
        for (Volunteer volunteer : volunteerList) {
            String user = update.callbackQuery().from().username();
            SendMessage sendMessage = new SendMessage(volunteer.getChatId(),
                    "Пользователь: @" + user + " просит с ним связаться.");
            telegramBot.execute(sendMessage);
        }
    }
}


