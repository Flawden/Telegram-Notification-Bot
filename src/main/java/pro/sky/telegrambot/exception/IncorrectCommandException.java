package pro.sky.telegrambot.exception;

public class IncorrectCommandException extends RuntimeException {

    public IncorrectCommandException() {
    }

    public IncorrectCommandException(String message) {
        super(message);
    }
}
