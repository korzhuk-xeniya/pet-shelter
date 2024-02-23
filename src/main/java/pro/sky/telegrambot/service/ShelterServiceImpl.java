package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.PhotoSize;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import pro.sky.telegrambot.Buttons;
import pro.sky.telegrambot.model.Animal;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.repository.ShelterRepository;
import pro.sky.telegrambot.repository.UserRepository;
import pro.sky.telegrambot.repository.VolunteerRepository;

import java.io.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.file.StandardOpenOption.CREATE_NEW;



//import static jdk.javadoc.internal.tool.Main.execute;


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
    private boolean photoCheckButton = false; // флаг на проверку нажатия кнопки
    private boolean reportCheckButton = false; // флаг на проверку нажатия кнопки
    private String namePhotoId;
    private final ReportService reportService;

    //    private boolean isCorrectNumber = false;
    private static final Pattern MESSAGE_PATTERN = Pattern.compile("^(\\+7)([0-9]{10})$");
    private String filePath;
    int sendMessageReport;
//   @Value(value = "${telegram.bot.admins}")
//    private List<String> admins;

    public ShelterServiceImpl(TelegramBot telegramBot, ShelterRepository repository,
                              Animal animal, VolunteerRepository volunteerRepository,
                              Buttons buttons, UserService userService,
                              VolunteerService volunteerService, UserRepository userRepository,
                              AnimalService animalService, ReportService reportService) {
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
        this.reportService = reportService;
    }


    @Override
    public void process(Update update) throws IOException {
        List<String> adminsVolunteers = new ArrayList<>();
        adminsVolunteers.add("xeny_sk");
        adminsVolunteers.add("d_prudnikov");

//        sendMessage(update.message().chat().id(), "для начала работы, отправь /start");
        if (update.message() == null && update.callbackQuery() == null && update.message().photo() == null) {
            logger.info("пользователь отправил пустое сообщение");
            return;
        }

        if (update.callbackQuery() == null) {
            Long chatId = update.message().chat().id();
            String message = update.message().text();
            Long userId = update.message().from().id();
            String userName = update.message().from().firstName();
            String userLastName = update.message().from().lastName();
            int messageId = update.message().messageId();
            Matcher matcher = MESSAGE_PATTERN.matcher(message);
            if (update.message() != null && matcher.find()) {
                userRepository.updateNumber(update.message().chat().id().intValue(), update.message().text());
                userService.saveUser(update, false);
                sendMenuButton(chatId, "Номер записан, Вам обязательно позвонят!");
            }

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
//            if (update.message().photo() != null && update.message().caption() != null) {
//                logger.info("пользователь отправил фото с заголовком");
//                reportService.savePhoto(update, update.message());
//            }
            List<Animal> animalList1 = new ArrayList<Animal>(animalService.allAnimals());
            for (Animal pet : animalList1) {
                if (update.message().text().equals(pet.getNameOfAnimal().toString())) {
                    sendMessage(chatId,
                            "Волонтер скоро свяжется с Вами, чтобы подтвердить Ваш выбор");
                    callAVolunteerForConfirmationOfSelection(update, pet);
                    userService.saveUser(update, true);
                    animalService.saveUserIdInAnimal(update, pet);
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
                    case "Взять животное" ->
                            changeMessage(messageId, chatId, "Отправьте кличку животного.", buttons.buttonMenu());

                    case "Оставить телефон для связи" -> {
                        changeMessage(messageId, chatId,
                                "Введите свой номер телефона в формате +71112223344", buttons.buttonMenu());
                    }
                    //блок “Прислать отчет о питомце”
                    case "Форма ежедневного отчета" -> {
                        takeDailyReportFormPhoto(chatId);
                        photoCheckButton = true; // Устанавливаем флаг в true после нажатия кнопки
                    }
                    //блок Волонтера
                    case "Отчеты" -> {
                        volunteerService.reviewListOfReports(update.callbackQuery().message().chat().id());
//                        sendImageFromFileId(String.valueOf(update.callbackQuery().message().chat().id()));
                    }
                    case "Отчет сдан" -> {
                        reportSubmitted(update);
                        volunteerService.reviewListOfReports(update.callbackQuery().message().chat().id());
                    }
                    case "Прислать отчет о питомце" -> petReportSelection(messageId, chatId);
                }

//

                if (photoCheckButton) { // Проверяем флаг перед выполнением checkDailyReport(update) и проверяеем, что пользователь прислал фото
                    if (!(update.message() == null)) {
                        PhotoSize photoSize = getPhoto(update);
                        File file = downloadPhoto(photoSize.fileId());

                        savePhotoToLocalFolder(file, update);
                        checkDailyReportPhoto(update);
                        photoCheckButton = false;
                        reportCheckButton = true;
                    }
                }
                if (reportCheckButton) { // Проверяем флаг перед выполнением checkDailyReport(update)
                    // и проверяеем, что пользователь прислал текст отчета
                    if (!(update.message() == null)) {
                        checkDailyReportMessage(update);
                        reportCheckButton = false;
                    }
                }
            }
        }

    }


    //Если отчет сдан
    private void reportSubmitted(Update update) {
        changeMessage(update.callbackQuery().message().chat().id(), "Отчет сдан");
        System.out.println(sendMessageReport);
        volunteerService.reportSubmitted((long) sendMessageReport);
    }


    /**
     * @param chatId запрос у пользователя "Прислать фото питомца"
     */
    private void takeDailyReportFormPhoto(Long chatId) {
        sendMessage(chatId, "Пришлите фото питомца");

    }

//    public void sendImageFromFileId(String chatId) {
//
//        SendPhoto sendPhotoRequest = new SendPhoto(chatId,
//                new InputFile("AgACAgIAAxkBAAIFfGTgeJPke7lK6kcjJVQutOAjH4S6AAIPzDEb0UQAAUtYMYlzKbeYagEAAwIAA3gAAzAE"));
//
//        telegramBot.execute(sendPhotoRequest);
//
//    }

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
     * @param chatId
     * @param messageText метод для отправки кнопок Волонтера для работы с отчетами
     */
    @Override
    public void sendButtonOfVolunteerForReports(Long chatId, String messageText) {
        logger.info("Был вызван метод для отправки кнопок Выбора животного", chatId, messageText);
        SendMessage sendMessage = new SendMessage(chatId, messageText);
        sendMessage.replyMarkup(buttons.buttonsOfVolunteerForReports());
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
     * метод для изменения сообщения с кнопками
     */
    private void changeMessage(int messageId, long chatIdInButton, String messageText, InlineKeyboardMarkup
            keyboardMarkup) {
        logger.info("Был вызван метод для изменения сообщения с кнопками", messageId, chatIdInButton, messageText, keyboardMarkup);
        EditMessageText editMessageText = new EditMessageText(chatIdInButton, messageId, messageText);

        editMessageText.replyMarkup(keyboardMarkup);

        telegramBot.execute(editMessageText);

    }

    /**
     * @param chatIdInButton
     * @param messageText    метод для изменения сообщения
     */
    @Override
    public void changeMessage(long chatIdInButton, String messageText) {
        logger.info("Был вызван метод для изменения сообщения ", chatIdInButton, messageText);
//        EditMessageText editMessageText = new EditMessageText(chatIdInButton,, messageText);
        SendMessage sendMessage = new SendMessage(chatIdInButton, messageText);
        telegramBot.execute(sendMessage);
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
     * @param update Отправка запроса на подтверждение выбора животного волонтером
     */
    public void callAVolunteerForConfirmationOfSelection(Update update, Animal pet) {
        logger.info("Был вызван метод для отправки запроса волонтеру на подтверждение выбора животного", update);
        List<Volunteer> volunteerList = volunteerRepository.findAll();
        for (Volunteer volunteer : volunteerList) {
            String user = update.message().from().username();
            SendMessage sendMessage = new SendMessage(volunteer.getChatId(),
                    "Пользователь: @" + user + " хочет усыновить животное " + pet.getNameOfAnimal() + " id: " +
                            pet.getId());
            telegramBot.execute(sendMessage);
        }
    }

    /**
     * Извлекает из update список объектов PhotoSize, которые представляют разный размер фотографий
     * Через стрим ищет самую большую фотографию и возвращает её.
     */
    public PhotoSize getPhoto(Update update) {
        if (!(update.message() == null) && !(update.message().photo() == null)) {
            List<PhotoSize> photos = Arrays.stream(update.message().photo()).toList();
            return photos.stream().max(Comparator.comparing(PhotoSize::fileSize)).orElse(null);


        }
        return null;
    }

    /**
     * Получаеем объект File содержащий информацию о файле по его индефикатору.
     */
    private File downloadPhoto(String fileId) {
        GetFile getFile = new GetFile(fileId);
        return telegramBot.execute(getFile).file();


    }
//    private void savePhoto(Update update, Message message) {
//
//        GetFile getFileRequest = new GetFile(message.photo()[0].fileId());
//
//        GetFileResponse getFileResponse = telegramBot.execute(getFileRequest);
//
//        try {
//
//            File file = getFileResponse.file();
//
//            byte[] fileContent = telegramBot.getFileContent(file);
//
//            reportService.uploadPicture(update.message().chat().id(), fileContent, file, update.message().caption());
//
//            telegramBot.execute(new SendMessage(update.message().chat().id(), "Отчет принят"));
//        } catch (IOException e) {
//            logger.error("Что-то пошло не так");
//            throw new RuntimeException(e);
//        }
//
//    }


    /**
     * Скачиваем файл, генерируем уникальное имя для него,
     * перемещаем в целевую директорию и возвращаем путь к сохраненному файлу
     */
    private Path savePhotoToLocalFolder(File file, Update update) throws IOException {
        PhotoSize photoSize = getPhoto(update);
        File photo = telegramBot.execute(new GetFile(photoSize.fileId())).file();
        String filePath = photo.filePath();

        // Генерируем уникальное имя файла с сохранением расширения
        namePhotoId = photoSize.fileId() + "." + "jpg";
        Path targetPath = Path.of("src/main/resources/pictures", namePhotoId);
        userService.saveUser(update, true);
        reportService.saveReportPhotoId(update, namePhotoId);
        Files.move(Path.of(filePath), targetPath, StandardCopyOption.REPLACE_EXISTING);
        return targetPath;
    }
//        __________________________________________________________________
//        PhotoSize photoSize = getPhoto(update);
//        String filePath = file. ();
//        java.io.File downloadedFile;
//
//        downloadedFile = download(update.message());
//
//        // Генерируем уникальное имя файла с сохранением расширения
//        namePhotoId = photoSize.fileId() + "." + "jpg";
//        Path targetPath = Path.of("src/main/resources/pictures", namePhotoId);
//        userService.saveUser(update, true);
//        reportService.saveReportPhotoId(update, namePhotoId);
//
//        Files.move(downloadedFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
//        _______________________________________________________________
//        PhotoSize photoSize = getPhoto(update);
//        namePhotoId = photoSize.fileId() + "." + "jpg";
//        String filePath = file.filePath();
//        logger.info("Был вызван метод для загрузки аватара студента в файл ", file, update);
//        Path targetPath = Path.of("src/main/resources/pictures", namePhotoId);
//        Files.createDirectories(filePath.getParent());
//        Files.deleteIfExists(targetPath);
//
//        try (InputStream is = downloadPhoto(update.message()).getInputStream();
//        OutputStream os = Files.newOutputStream(targetPathh, CREATE_NEW);
//             BufferedInputStream bis = new BufferedInputStream(is, 1024);
//             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
//        ) {
//            bis.transferTo(bos);
//        }
//        return targetPath;
//    }

    /**
     * @param update проверка что пользователь прислал текстовую часть отчета и сохроняем в БД
     */

    private void checkDailyReportMessage(Update update) {
        long chatId = update.message().chat().id();
        reportService.dailyReportCheckMessage(chatId, update, namePhotoId);

    }

    /**
     * @param update проверка что пользователь прислал фото для отчета и сохроняем в БД
     */
    private void checkDailyReportPhoto(Update update) {
        long chatId = update.message().chat().id();
        reportService.dailyReportCheckPhoto(chatId, update);
    }

    //метод кнопки "Прислать отчет о питомце"
    private void petReportSelection(int messageId, long chatId) {

        changeMessage(messageId, chatId, "Выберите одну из кнопок", buttons.buttonsOfOwner());
    }

}



