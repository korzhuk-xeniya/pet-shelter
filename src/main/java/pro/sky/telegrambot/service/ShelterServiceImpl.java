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
import pro.sky.telegrambot.model.Animal;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.repository.ShelterRepository;
import pro.sky.telegrambot.repository.UserRepository;
import pro.sky.telegrambot.repository.VolunteerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class ShelterServiceImpl implements ShelterService {
    private final TelegramBot telegramBot;
    private final ShelterRepository repository;
    private final Animal animal;
    private final VolunteerRepository volunteerRepository;
    private final UserRepository userRepository;
    private final Buttons buttons;
    private final UserService userService;
    private final VolunteerService volunteerService;
    private final AnimalService animalService;
    private final Logger logger = LoggerFactory.getLogger(ShelterServiceImpl.class);

    private boolean isCorrectNumber = false;
    private static final Pattern MESSAGE_PATTERN = Pattern.compile("^(\\+7)([0-9]{10})$");
//   @Value(value = "${telegram.bot.admins}")
//    private List<String> admins;

    public ShelterServiceImpl(TelegramBot telegramBot, ShelterRepository repository,
                              Animal animal, VolunteerRepository volunteerRepository, Buttons buttons, UserService userService,
                              VolunteerService volunteerService, UserRepository userRepository,
                              AnimalService animalService) {
        this.telegramBot = telegramBot;
        this.repository = repository;
        this.animal = animal;
        this.volunteerRepository = volunteerRepository;
        this.buttons = buttons;
        this.userService = userService;
        this.volunteerService = volunteerService;

//        this.admins = admins;
        this.userRepository = userRepository;
        this.animalService = animalService;
//
    }


    @Override
    public void process(Update update) {
        List<String> adminsVolunteers = new ArrayList<>();
//        adminsVolunteers.add("xeny_sk");
        adminsVolunteers.add("d_prudnikov");

//        sendMessage(update.message().chat().id(), "для начала работы, отправь /start");
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
//            Pattern pattern = Pattern.compile("^(\\+7)([0-9]{10})$");
            Matcher matcher = MESSAGE_PATTERN.matcher(message);
            if (update.message() != null && matcher.find()) {
                userRepository.updateNumber(update.message().chat().id().intValue(), update.message().text());
                userService.saveUser(update, false);
                sendMenuButton(chatId, "Номер записан, Вам обязательно позвонят!");


                isCorrectNumber = false;
            } else if
            (update.message() != null && !update.message().text().equals("/start") && !matcher.find()) {
                logger.info("пользователь отправил  сообщение  с неопределенным содержанием");
                sendMessage(chatId, "Содержание не определено.");
                return;

            }
//            else
//            if  ((!update.message().text().equals("/start")) && (!matcher.find())) {
//                logger.info("пользователь отправил  сообщение  с неопределенным содержанием");
//
//
//                sendMessage(chatId, "для начала работы, отправь /start");
//                return;
//            }
//            Pattern pattern = Pattern.compile("^(\\+7)([0-9]{10})$");
//            Pattern pattern = Pattern.compile("^\\+?[1-9][0-9]{7,14}$");

            if (update.message().text().equals("/start")) {
                logger.info("пользователь отправил /start");
                sendMenuButton(chatId, " Добро пожаловать в PetShelterBot, "
                        + update.message().from().firstName() + "! Я помогаю взаимодействовать с приютами для животных!");
                if (adminsVolunteers.contains(update.message().from().username())) {
                    logger.info("пользователь есть среди администраторов");
                    volunteerService.saveVolunteer(update);
//
                    sendMenuVolunteer(chatId, "Перед Вами меню волонтера");
//                    logger.info("волонтеру отправлено меню");
                } else {
                    userService.saveUser(update, false);
                }
            }
            for (Animal pet:animalService.allAnimals()) {
                if(update.message().text().equals(animal.getNameOfAnimal())) {

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
                            changeMessage(messageId, chatId, "Прежде, чем взять животное из приюта, ознакомьтесь с информацией," +
                                    "которая представленна ниже", buttons.takeAnimalButton());


                    case "Позвать волонтера" -> {
                        callAVolunteer(update);
                        changeMessage(messageId, chatId, "Волонтер скоро свяжется с Вами", buttons.buttonMenu());
                    }
                    case "Выбрать животное" -> {
                        List<Animal> animalList = new ArrayList<Animal>(animalService.allAnimals());
                        for (Animal animal2 : animalList) {
                            sendButtonChooseAnimal(chatId, "Кличка животного:" + animal2.getNameOfAnimal() +
                                    "; Возраст: " + animal2.getAgeMonth() + " месяцев; Тип животного: " +
                                    animal2.getPetType() + ";Фото:" + animal2.getPhotoLink());

                        }
                    }
                    case "Взять животное" ->{
                        sendMessageByUserId(update, userId,"Отправьте кличку животного" );
                    }


//                    {
//                        callAVolunteerForConfirmationOfSelection(update);
//                        changeMessage(messageId, chatId,
//                                "Волонтер скоро свяжется с Вами, чтобы подтвердить Ваш выбор",
//                                buttons.buttonMenu());
////                        userService.saveUser(update, true);
//                    }


//
                    case "Оставить телефон для связи" -> {
                        changeMessage(messageId, chatId,
                                "Введите свой номер телефона в формате +71112223344", buttons.buttonMenu());
//
                    }
//case "Прислать отчет о питомце" -> petReportSelection(messageId, chatId);
//
////Блок "Информация о приюте"
////
////                case "Информация о приюте для собак" -> aboutDogShelterSelection(messageId, chatId);
////                case "Расписание работы приюта для собак" -> dogShelterWorkingHoursSelection(messageId, chatId);
////                case "Контакты охраны приюта для собак" -> dogShelterSecurityContactSelection(messageId, chatId);
////                case "Общие правила поведения" -> safetyRecommendationsSelection(messageId, chatId);
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
//        Pattern pattern = Pattern.compile("^(\\+7)([0-9]{10})$");
//        if (update.message() != null && isCorrectNumber && pattern.matcher(update.message().text()).matches()) {
//            userService.saveUser(update, false);
//            userRepository.updateNumber(update.message().chat().id().intValue(), update.message().text());
//            changeMessage(messageId, chatId, "Добро пожаловать в наш приют для собак!",
//                    buttons.buttonsInformationAboutShelter());
//
//            executeSendMessage(new SendMessage(update.getMessage().getChatId().toString(), "номер записан вам обязательно позвонят"));
//            isCorrectNumber = false;
//        } else if (update.message() != null && isCorrectNumber && !pattern.matcher(update.message().text()).matches()) {
//            executeSendMessage(new SendMessage(update.getMessage().getChatId().toString(), "не правильно набран номер повторите ещё раз"));
//        }
    }

    @Override
    public void sendMessage(Long chatId, String messageText) {
        logger.info("Был вызван метод для отправки сообщения", chatId, messageText);
        SendMessage sendMessage = new SendMessage(chatId, messageText);
        telegramBot.execute(sendMessage);
    }
    @Override
    public void sendMessageByUserId(Update update, Long userID, String messageText) {
        logger.info("Был вызван метод для отправки сообщения по id", userID, messageText);
        SendMessage sendMessage = new SendMessage(update.callbackQuery().message().chat().id(), messageText);
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
     * метод для отправки кнопок "Меню волонтера"
     */
    @Override
    public void sendMenuVolunteer(Long chatId, String messageText) {
        logger.info("Был вызван метод для отправки кнопок Меню волонтера", chatId, messageText);
        SendMessage sendMessage = new SendMessage(chatId, messageText);
        sendMessage.replyMarkup(buttons.buttonsOfVolunteer());
        telegramBot.execute(sendMessage);
    }

    /**
     * метод для отправки кнопок "Выбрать животное"
     */
    @Override
    public void sendButtonChooseAnimal(Long chatId, String messageText) {
        logger.info("Был вызван метод для отправки кнопок Выбора животного", chatId, messageText);
        SendMessage sendMessage = new SendMessage(chatId, messageText);
        sendMessage.replyMarkup(buttons.buttonOfChooseAnimal());
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
    private void changeMessage(int messageId, long chatIdInButton, String messageText, InlineKeyboardMarkup
            keyboardMarkup) {
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
     * @param update Реализация кнопки "Позвать волонтера"
     */
    public void callAVolunteer(Update update) {
        logger.info("Был вызван метод для вызова волонтера", update);
        List<Volunteer> volunteerList = volunteerRepository.findAll();
        for (Volunteer volunteer : volunteerList) {
            String user = update.callbackQuery().from().username();
            SendMessage sendMessage = new SendMessage(volunteer.getChatId(),
                    "Пользователь: @" + user + " просит с ним связаться.");
            telegramBot.execute(sendMessage);
        }
    }

    /**
     * @param update
     * Отправка запроса на подтверждение выбора животного волонтером
     */
    public void callAVolunteerForConfirmationOfSelection(Update update) {
        logger.info("Был вызван метод для отправки запроса волонтеру на подтверждение выбора животного", update);
        List<Volunteer> volunteerList = volunteerRepository.findAll();
        for (Volunteer volunteer : volunteerList) {
            String user = update.callbackQuery().from().username();
            SendMessage sendMessage = new SendMessage(volunteer.getChatId(),
                    "Пользователь: @" + user + " хочет усыновить животное.");
            telegramBot.execute(sendMessage);
        }
    }


}



