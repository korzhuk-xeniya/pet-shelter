package pro.sky.telegrambot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

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
    private long userId;

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



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", ageMonth=" + ageMonth +
                ", nameOfAnimal='" + nameOfAnimal + '\'' +
                ", photoLink='" + photoLink + '\'' +
                ", gender='" + gender + '\'' +
                ", petType='" + petType + '\'' +
                ", userId=" + userId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return id == animal.id && ageMonth == animal.ageMonth && userId == animal.userId && Objects.equals(nameOfAnimal, animal.nameOfAnimal) && Objects.equals(photoLink, animal.photoLink) && Objects.equals(gender, animal.gender) && Objects.equals(petType, animal.petType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ageMonth, nameOfAnimal, photoLink, gender, petType, userId);
    }
}

