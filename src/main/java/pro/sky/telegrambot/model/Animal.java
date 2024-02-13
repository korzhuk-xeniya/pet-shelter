package pro.sky.telegrambot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@AllArgsConstructor
//@NoArgsConstructor
@Entity
@Table(name = "animals")
public class Animal {

    //ID животного
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_animal")
    private long id;
    @Column(name = "age_in_month")
    //Возраст животного
    private final long ageMonth;
    @Column(name = "name_of_animal")
    //Имя
    private final String nameOfAnimal;
    @Column(name = "photo_link")
    //Ссылка на фото
    private final String photoLink;
    @Column(name = "gender")
    //Пол животного
    private final String gender;
    @Column(name = "pet_type")
    //Тип животного
    private final String petType;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Animal(long ageMonth, String nameOfAnimal, String photoLink, String gender, String petType) {
        this.ageMonth = ageMonth;
        this.nameOfAnimal = nameOfAnimal;
        this.photoLink = photoLink;
        this.gender = gender;
        this.petType = petType;
    }

    public Animal(long id, long ageMonth, String nameOfAnimal, String photoLink, String gender, String petType) {
        this.id = id;
        this.ageMonth = ageMonth;
        this.nameOfAnimal = nameOfAnimal;
        this.photoLink = photoLink;
        this.gender = gender;
        this.petType = petType;
    }

    public Animal() {

        ageMonth = 0;
        nameOfAnimal = null;
        photoLink = null;
        gender = null;
        petType = null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getAgeMonth() {
        return ageMonth;
    }

    public String getNameOfAnimal() {
        return nameOfAnimal;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public String getGender() {
        return gender;
    }

    public String getPetType() {
        return petType;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "user=" + user +
                ", ageMonth=" + ageMonth +
                ", name='" + nameOfAnimal + '\'' +
                ", photoLink='" + photoLink + '\'' +
                ", gender='" + gender + '\'' +
                ", petType='" + petType + '\'' +
                '}';
    }


}

