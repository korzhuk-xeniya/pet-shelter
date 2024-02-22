package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.exception.VolunteerNotFoundException;
import pro.sky.telegrambot.model.Report;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.repository.ReportRepository;
import pro.sky.telegrambot.repository.VolunteerRepository;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class VolunteerServiceImpl implements VolunteerService {
    private final VolunteerRepository volunteerRepository;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final ReportRepository reportRepository;
    private final ShelterService shelterService;
    private final ReportService reportService;

    public VolunteerServiceImpl(VolunteerRepository volunteerRepository, ReportRepository reportRepository, ShelterService shelterService, ReportService reportService) {
        this.volunteerRepository = volunteerRepository;
        this.reportRepository = reportRepository;
        this.shelterService = shelterService;
        this.reportService = reportService;
    }

    /**
     * @param volunteer
     * @return волонтера, который был добавлен в базу данных
     *
     * добавление нового волонтера
     */
    @Override
    public Volunteer saveVolunteerInBd(Volunteer volunteer) {
        logger.info("Был вызван метод для добавления нового волонтера в базу данных", volunteer);

        return volunteerRepository.save(volunteer);
    }

    /**
     * @param id
     * @return волонтера по id
     */
    @Override
    public Volunteer readVolunteer(long id) {
        logger.error("Был вызван метод для выбрасывания ошибки если волонтер не найден по id", id);
        return (Volunteer) volunteerRepository.findById(id).
                orElseThrow(() -> new VolunteerNotFoundException("Волонтер с id " + id + " не найден в базе данных"));
    }

    /**
     * @param volunteer
     * @return волонтера с обновленными данными
     */
    @Override
    public Volunteer updateVolunteerById(Volunteer volunteer) {
        logger.info("Был вызван метод для обновления волонтера", volunteer);
        readVolunteer(volunteer.getId());
        return volunteerRepository.save(volunteer);
    }

    /**
     * @param id
     *
     * удаление волонтера из базы данных
     */
    @Override
    public void deleteById(long id) {
        logger.info("Был вызван метод для удаления волонтера", id);
        volunteerRepository.deleteById(id);
    }


    /**
     * @return список всех волонтеров
     *
     * ищет всех волонтеров
     */
    @Override

    public List<Volunteer> findAllVolunteers() {
        logger.info("Был вызван метод для поиска всех волонтеров");
        return volunteerRepository.findAll().stream().toList();
    }

    /**
     * @param chatId
     * @return
     * получение волонтера из базы данных
     */
    @Override

    public Optional<Volunteer> getVolunteerByChatId(long chatId) {
        logger.info("Был вызван метод для получения пользователя из базы данных", chatId);
        return volunteerRepository.findByChatId(chatId);
    }
    @Override
    /**
     * Поиск волонтера по chatId, если он есть то обновляем, если нет, создается новый волонтер
     */
    public void saveVolunteer(Update update) {
        logger.info("Был вызван метод для сохранения волонтера в базу данных", update);
        int chatId = update.message().chat().id().intValue();
        Optional<Volunteer> volunteerOptional = getVolunteerByChatId(chatId);

        if (volunteerOptional.isPresent()) {
            logger.info("Волонтер уже есть в базе данных");
            Volunteer volunteer = volunteerOptional.get();

            updateVolunteerById(volunteer);
        } else {
            Volunteer newVolunteer = new Volunteer(update.message().from().firstName(),update.message().from().lastName(),
                    chatId);
            saveVolunteerInBd(newVolunteer);
        }
    }
    @Override
    /**
            * Получаем непроверенный отчет из всех отчетов
     *
             * @return возвращает первый непроверенный отчет и кнопки действия с отчетом
     */
    public void reviewListOfReports(Long chatIdOfVolunteer) {
        List<Report> reportList = reportRepository.findReportByCheckReportIsFalse();
        if (reportList.isEmpty()) {
             shelterService.sendMessage(chatIdOfVolunteer, "Нет непроверенных отчетов.");


        } else {
            for (Report report : reportList) {
                shelterService.sendMessage(chatIdOfVolunteer, "Отчет #" + report.getId() + "\n" +
                        "Текстовая часть отчета: " + report.getGeneralWellBeing());
                shelterService.sendButtonOfVolunteerForReports(chatIdOfVolunteer,"Необходимо проверить отчет!");


            }
        }

    }
@Override
    /**
     * Обновляем в БД отчет и ставим, что отчет сдан
     */
    public void reportSubmitted(Long idReport) {
        Report report = reportRepository.findReportById(idReport);
        report.setCheckReport(true);
        reportService.updateReport(report);
    }

    @Override
    /**
     * Позволяет распарсить SendMessage из метода reviewListOfReports что-бы достать ID репорта
     * с которым будем работать.
     *
     * @param reportString получаем строку SendMessage
     * @return вовзращает ID отчета
     */
    public int parseReportNumber(String reportString) {
        Pattern pattern = Pattern.compile("Отчет #(\\d+)");
        Matcher matcher = pattern.matcher(reportString);

        if (matcher.find()) {
            String numberStr = matcher.group(1);
            return Integer.parseInt(numberStr);
        }
        return -1; // В случае, если не удалось извлечь номер отчета
    }
}
