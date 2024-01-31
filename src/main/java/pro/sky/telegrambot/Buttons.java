package pro.sky.telegrambot;



import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Buttons {
    public InlineKeyboardMarkup buttonMenu() {
        InlineKeyboardButton menuButton = new InlineKeyboardButton("Меню");
        menuButton.callbackData("Меню");
        return new InlineKeyboardMarkup(new InlineKeyboardButton[]{menuButton});
    }
    public InlineKeyboardMarkup buttonsOfStart() {
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
        InlineKeyboardButton aboutShelterButton = new InlineKeyboardButton("О приюте");
        InlineKeyboardButton callVolunteerButton = new InlineKeyboardButton("Позвать волонтера");
//        InlineKeyboardButton timetableButton = new InlineKeyboardButton("График работы");
//        InlineKeyboardButton addressButton = new InlineKeyboardButton("Адрес приюта");
//        InlineKeyboardButton locationMapButton = new InlineKeyboardButton("Схема проезда");
//        InlineKeyboardButton securityButton = new InlineKeyboardButton("Телефон охраны для оформления пропуска");
//        InlineKeyboardButton safetyButton = new InlineKeyboardButton("Техника безопасности на территории приюта");
        InlineKeyboardButton leavePhoneNumberButton = new InlineKeyboardButton("Оставить телефон для связи");
        InlineKeyboardButton toStart = new InlineKeyboardButton("В начало");
        aboutShelterButton.callbackData("О приюте");
        callVolunteerButton.callbackData("Позвать волонтера");
//        timetableButton.callbackData("График работы");
//        addressButton.callbackData("Адрес приюта");
//        locationMapButton.callbackData("Схема проезда");
//        securityButton.callbackData("Телефон охраны для оформления пропуска");
//        safetyButton.callbackData("Техника безопасности на территории приюта");
        leavePhoneNumberButton.callbackData("Оставить телефон для связи");
        toStart.callbackData("В начало");

        return new InlineKeyboardMarkup(new InlineKeyboardButton[]{aboutShelterButton},
//                new InlineKeyboardButton[]{timetableButton},
//                new InlineKeyboardButton[]{addressButton},
//                new InlineKeyboardButton[]{locationMapButton},
//                new InlineKeyboardButton[]{securityButton},
//                new InlineKeyboardButton[]{safetyButton},
                new InlineKeyboardButton[]{leavePhoneNumberButton},
                new InlineKeyboardButton[]{callVolunteerButton},
                new InlineKeyboardButton[]{toStart});
    }
//    public InlineKeyboardMarkup takeAnimalButton() {
//        InlineKeyboardButton datingRulesButton = new InlineKeyboardButton("Правила знакомства");
//        InlineKeyboardButton listOfDocumentsButton = new InlineKeyboardButton("Список документов");
//        InlineKeyboardButton TransportationButton = new InlineKeyboardButton("Рекомендации по транспортировке животных");
//        InlineKeyboardButton arrangementPuppyButton = new InlineKeyboardButton();
//        InlineKeyboardButton arrangementAdultButton = new InlineKeyboardButton();
//        arrangementAdultButton.setText("Обустройство для взрослой собаки");
//        arrangementAdultButton.setCallbackData("Обустройство для взрослой собаки");
//        arrangementPuppyButton.setText("Обустройство для щенка");
//        arrangementPuppyButton.setCallbackData("Обустройство щенка");
//        InlineKeyboardButton arrangemenDisabledButton = new InlineKeyboardButton("Обустройство для животного\n с ограниченными возможностями");
//        InlineKeyboardButton whyRefuseButton = new InlineKeyboardButton("Cписок причин, почему могут отказать");
//        InlineKeyboardButton contactButton = new InlineKeyboardButton("Запись ваших контактов");
//        InlineKeyboardButton callVolunteerButton = new InlineKeyboardButton("Позвать волонтера");
//        InlineKeyboardButton toStart = new InlineKeyboardButton("В начало");
//        datingRulesButton.setCallbackData("Правила знакомства");
//        listOfDocumentsButton.setCallbackData("Список документов");
//        TransportationButton.setCallbackData("Рекомендации по транспортировке");
//        arrangemenDisabledButton.setCallbackData("Обустройство для ограниченного");
//        whyRefuseButton.setCallbackData("Cписок причин");
//        contactButton.setCallbackData("Запись контактов");
//        callVolunteerButton.setCallbackData("Позвать волонтера");
//        toStart.setCallbackData("В начало");
//        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
//        List<List<InlineKeyboardButton>> rowsInLine = List.of(
//                List.of(datingRulesButton), List.of(listOfDocumentsButton),
//                List.of(TransportationButton), List.of(arrangementPuppyButton),List.of(arrangementAdultButton),
//                List.of(arrangemenDisabledButton), List.of(whyRefuseButton),List.of(contactButton),
//                List.of(callVolunteerButton), List.of(toStart)
//        );
//        keyboardMarkup.setKeyboard(rowsInLine);
//        return keyboardMarkup;
//    }
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
