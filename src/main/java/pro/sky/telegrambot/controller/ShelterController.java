package pro.sky.telegrambot.controller;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.telegrambot.service.ShelterService;

@RestController
public  class ShelterController {
    private ShelterService shelterService;
    private TelegramBot telegramBot;

    public ShelterController(ShelterService shelterService, TelegramBot telegramBot) {
        this.shelterService = shelterService;
        this.telegramBot = telegramBot;
    }
//    public BotApiMethod<> process(@RequestBody Update update) {
//        return shelterService.process(update);
//    }
}
