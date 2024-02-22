package pro.sky.telegrambot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "report_tg")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private long id;

    @Column(name = "date_added")
    private LocalDateTime dateAdded;

    @Column(name = "general_well_being")
    private String generalWellBeing;

    @Column(name = "photo_name")
    private String photoNameId;

    @Column(name = "check_report")
    private boolean checkReport;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
