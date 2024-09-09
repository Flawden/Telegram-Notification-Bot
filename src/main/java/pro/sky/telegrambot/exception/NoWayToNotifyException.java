package pro.sky.telegrambot.exception;

public class NoWayToNotifyException extends RuntimeException {
    public NoWayToNotifyException() {
    }

    public NoWayToNotifyException(String message) {
        super(message);
    }
}
