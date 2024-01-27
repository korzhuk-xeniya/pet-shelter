package pro.sky.telegrambot.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

public class Shelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID id;


    @Column(name = "nameOfShelter")
    private String nameOfShelter;
    @Column(name = "aboutShelter")
    private  String informationAboutShelter;
    @Column(name = "address")
    private String address;
    @Column(name="phoneNumber")
    private String phoneNumber;
}
