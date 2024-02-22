package pro.sky.telegrambot.repository;

import liquibase.pro.packaged.R;
import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.Report;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
//    Optional<Report> findByUserId(Long studentId);
    Optional<Report> findById(Long id);

    Optional<Report> findReportByPhotoNameId(String namePhotoId);
    List<Report> findReportByCheckReportIsFalse();

    Report findReportById(Long idReport);
}
