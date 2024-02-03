package pro.sky.telegrambot.exception;

public class VolunteerNotFoundException extends RuntimeException {
    public VolunteerNotFoundException(String message) {
        super(message);
    }
}
