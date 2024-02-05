package pro.sky.telegrambot.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "volunteers")
public class Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_volunteer", nullable = false, unique = true)
    private UUID id;



    @Column(name = "name_volunteer")
    private String name;
    @Column(name = "last_name_volunteer")
    private String lastName;
    @Column(name = "chat_id_volunteer")
    private long chatId;
    public Volunteer() {
    }

    public Volunteer(String name, String lastName, long chatId) {
        this.name = name;
        this.lastName = lastName;
        this.chatId = chatId;
    }
    public Volunteer(UUID id, String name, String lastName, long chatId) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.chatId = chatId;
    }
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    @Override
    public String toString() {
        return "Volunteer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", chatId=" + chatId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Volunteer volunteer = (Volunteer) o;
        return chatId == volunteer.chatId && Objects.equals(id, volunteer.id) && Objects.equals(name, volunteer.name) && Objects.equals(lastName, volunteer.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName, chatId);
    }
}
