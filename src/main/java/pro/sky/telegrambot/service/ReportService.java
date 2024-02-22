package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.data.domain.Page;
import pro.sky.telegrambot.model.Report;

public interface ReportService {
    void updateReport(Report report);

    Report findReport(long userId);


    Page<Report> getAllReports(Integer pageNo, Integer pageSize);

    /**
     * Запрашива у пользователя фото
     */
    SendMessage sendMessageDailyReportPhoto(long chatId);



    /**
         * Проверяем, что пользователь прислал текст для отчета.
         */
    SendMessage dailyReportCheckMessage(long chatId, Update update, String namePhotoId);

    /**
         * Поиск пользователя по chatId, если он есть то обновляем dateTimeToTook, если нет, создается новый пользователь
         */
//    void saveUser(Update update, boolean tookAPET);

    /**
         * Сохранение отчета в БД
         */
    void saveReportPhotoId(Update update, String namePhotoId);

    /**
     * Проверяем, что пользователь прислал фото для отчета.
     */
    SendMessage dailyReportCheckPhoto(long chatId, Update update);
}
