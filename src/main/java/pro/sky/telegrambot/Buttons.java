package pro.sky.telegrambot;


import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.service.ShelterServiceImpl;

import java.util.List;

@Component
public class Buttons {
    private final Logger logger = LoggerFactory.getLogger(Buttons.class);

    public InlineKeyboardMarkup buttonMenu() {
        logger.info("Был вызван метод создания кнопки Меню");
        InlineKeyboardButton menuButton = new InlineKeyboardButton("Меню");
        menuButton.callbackData("Меню");
        return new InlineKeyboardMarkup(new InlineKeyboardButton[]{menuButton});
    }

    public InlineKeyboardMarkup buttonsOfStart() {
        logger.info("Был вызван метод создания кнопок 0 этапа");
        InlineKeyboardButton checkInfoButton = new InlineKeyboardButton("Информация о приюте");
        InlineKeyboardButton callVolunteerButton = new InlineKeyboardButton("Позвать волонтера");
        InlineKeyboardButton getReportAboutPet = new InlineKeyboardButton("Прислать отчет о питомце");
        InlineKeyboardButton howGetPet = new InlineKeyboardButton("Как взять животное из приюта?");
        InlineKeyboardButton toStart = new InlineKeyboardButton("В начало");
        checkInfoButton.callbackData("Информация о приюте");
        callVolunteerButton.callbackData("Позвать волонтера");
        getReportAboutPet.callbackData("Прислать отчет о питомце");
        howGetPet.callbackData("Как взять животное из приюта?");
        toStart.callbackData("В начало");

        return new InlineKeyboardMarkup(new InlineKeyboardButton[]{checkInfoButton},
                new InlineKeyboardButton[]{getReportAboutPet},
                new InlineKeyboardButton[]{howGetPet},
                new InlineKeyboardButton[]{callVolunteerButton},
                new InlineKeyboardButton[]{toStart});
    }

    public InlineKeyboardMarkup buttonsInformationAboutShelter() {
        logger.info("Был вызван метод создания кнопок Инфомация о приюте");
        InlineKeyboardButton aboutShelterButton = new InlineKeyboardButton("О приюте");
        InlineKeyboardButton callVolunteerButton = new InlineKeyboardButton("Позвать волонтера");
        InlineKeyboardButton timetableButton = new InlineKeyboardButton("График работы");
        InlineKeyboardButton addressButton = new InlineKeyboardButton("Адрес приюта");
        InlineKeyboardButton locationMapButton = new InlineKeyboardButton("Схема проезда");
        InlineKeyboardButton securityButton = new InlineKeyboardButton("Телефон охраны");
        InlineKeyboardButton safetyButton = new InlineKeyboardButton("Безопасность на территории приюта");
        InlineKeyboardButton leavePhoneNumberButton = new InlineKeyboardButton("Оставить телефон для связи");
        InlineKeyboardButton toStart = new InlineKeyboardButton("В начало");
        aboutShelterButton.callbackData("О приюте");
        callVolunteerButton.callbackData("Позвать волонтера");
        timetableButton.callbackData("График работы");
        addressButton.callbackData("Адрес приюта");
        locationMapButton.callbackData("Схема проезда");
        securityButton.callbackData("Телефон охраны");
        safetyButton.callbackData("Безопасность на территории приюта");
        leavePhoneNumberButton.callbackData("Оставить телефон для связи");
        toStart.callbackData("В начало");

        return new InlineKeyboardMarkup(new InlineKeyboardButton[]{aboutShelterButton}
                ,
                new InlineKeyboardButton[]{timetableButton}
                ,
                new InlineKeyboardButton[]{addressButton}
                ,
                new InlineKeyboardButton[]{locationMapButton}
                ,
                new InlineKeyboardButton[]{securityButton}
                ,
                new InlineKeyboardButton[]{safetyButton},
                new InlineKeyboardButton[]{leavePhoneNumberButton},
                new InlineKeyboardButton[]{callVolunteerButton},
                new InlineKeyboardButton[]{toStart}
        );
    }

    public InlineKeyboardMarkup takeAnimalButton() {
        logger.info("Был вызван метод создания кнопок Как взять животное из приюта");
        InlineKeyboardButton datingRulesButton = new InlineKeyboardButton("Правила знакомства");
        InlineKeyboardButton listOfDocumentsButton = new InlineKeyboardButton("Список документов");
        InlineKeyboardButton transportationButton = new InlineKeyboardButton("Рекомендации по транспортировке");
        InlineKeyboardButton arrangementPuppyButton = new InlineKeyboardButton("Обустройство щенка");
        InlineKeyboardButton arrangementAdultButton = new InlineKeyboardButton("Обустройство для взрослой собаки");
        InlineKeyboardButton arrangementDisabledButton = new InlineKeyboardButton("Животное с ОВЗ");
        InlineKeyboardButton whyRefuseButton = new InlineKeyboardButton("Причины отказа");
        InlineKeyboardButton chooseAnimalButton = new InlineKeyboardButton("Выбрать животное");
        InlineKeyboardButton callVolunteerButton = new InlineKeyboardButton("Позвать волонтера");
        InlineKeyboardButton toStart = new InlineKeyboardButton("В начало");

        arrangementAdultButton.callbackData("Обустройство для взрослой собаки");
        arrangementPuppyButton.callbackData("Обустройство щенка");
        datingRulesButton.callbackData("Правила знакомства");
        listOfDocumentsButton.callbackData("Список документов");
        transportationButton.callbackData("Рекомендации по транспортировке");
        arrangementDisabledButton.callbackData("Животное с ОВЗ");
        whyRefuseButton.callbackData("Причины отказа");
        chooseAnimalButton.callbackData("Выбрать животное");
        callVolunteerButton.callbackData("Позвать волонтера");
        toStart.callbackData("В начало");
        return new InlineKeyboardMarkup(new InlineKeyboardButton[]{datingRulesButton},
                new InlineKeyboardButton[]{listOfDocumentsButton},
                new InlineKeyboardButton[]{arrangementDisabledButton},
                new InlineKeyboardButton[]{whyRefuseButton},
                new InlineKeyboardButton[]{arrangementPuppyButton},
                new InlineKeyboardButton[]{arrangementAdultButton},
                new InlineKeyboardButton[]{transportationButton},
                new InlineKeyboardButton[]{chooseAnimalButton},
                new InlineKeyboardButton[]{callVolunteerButton},
                new InlineKeyboardButton[]{toStart});
    }

    /**
     * Кнопки  волонтерской панели
     */
    public InlineKeyboardMarkup buttonsOfVolunteer() {
        logger.info("Был вызван метод создания кнопок Волонтера");
        InlineKeyboardButton reportButton = new InlineKeyboardButton("Просмотр отчетов");

        InlineKeyboardButton toStart = new InlineKeyboardButton("В начало");


        reportButton.callbackData("Просмотр отчетов");

        toStart.callbackData("В начало");
        return new InlineKeyboardMarkup(new InlineKeyboardButton[]{reportButton},
                new InlineKeyboardButton[]{toStart});
    }

    public InlineKeyboardMarkup buttonOfChooseAnimal() {
        logger.info("Был вызван метод создания кнопок Выбрать животное");
        InlineKeyboardButton tookButton = new InlineKeyboardButton("Взять животное");
        InlineKeyboardButton toStart = new InlineKeyboardButton("В начало");
        tookButton.callbackData("Взять животное");
        toStart.callbackData("В начало");
        return new InlineKeyboardMarkup(new InlineKeyboardButton[]{tookButton});
    }
//    public InlineKeyboardMarkup shelterInformationButton() {
//        InlineKeyboardButton safetyRecommendationsButton = new InlineKeyboardButton("Общие правила поведения");
//        InlineKeyboardButton recordingContactsSelection = new InlineKeyboardButton("Запись ваших контактов");
//        InlineKeyboardButton toStart = new InlineKeyboardButton("В начало");
//        InlineKeyboardButton aboutShelterButton = new InlineKeyboardButton();
//        InlineKeyboardButton shelterWorkingHoursButton = new InlineKeyboardButton();
//        InlineKeyboardButton shelterSecurityContactButton = new InlineKeyboardButton();
//        aboutShelterButton.setText("Информация о приюте для собак");
//        aboutShelterButton.setCallbackData("Информация о приюте для собак");
//        shelterWorkingHoursButton.setText("Расписание приюта для собак");
//        shelterWorkingHoursButton.setCallbackData("Расписание приюта для собак");
//        shelterSecurityContactButton.setText("Контакты охраны приюта для собак");
//        shelterSecurityContactButton.setCallbackData("Контакты охраны приюта для собак");
//        safetyRecommendationsButton.setCallbackData("Общие правила поведения");
//        recordingContactsSelection.setCallbackData("Запись ваших контактов");
//        toStart.setCallbackData("В начало");
//        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
//        List<List<InlineKeyboardButton>> rowsInLine = List.of(
//                List.of(aboutShelterButton), List.of(shelterWorkingHoursButton),
//                List.of(shelterSecurityContactButton),
//                List.of(safetyRecommendationsButton), List.of(recordingContactsSelection), List.of(toStart)
//        );
//        keyboardMarkup.setKeyboard(rowsInLine);
//        return keyboardMarkup;
//    }
}
