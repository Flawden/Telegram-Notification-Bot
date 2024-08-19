package pro.sky.telegrambot.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification_task")
public class NotificationTask {

    public NotificationTask() {
    }

    public NotificationTask(Long chatId, String notificationMessage, LocalDateTime dateTime) {
        this.chatId = chatId;
        this.notificationMessage = notificationMessage;
        this.dateTime = dateTime;
    }

    public NotificationTask(Long id, String notificationMessage, Long chatId, LocalDateTime dateTime) {
        this.id = id;
        this.notificationMessage = notificationMessage;
        this.chatId = chatId;
        this.dateTime = dateTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long chatId;
    private String notificationMessage;
    private LocalDateTime dateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Уведомление: " + "\n" +
                "id: " + id + "\n" +
                "chatId: " + chatId + "\n" +
                "notificationMessage: " + notificationMessage + "\n" +
                "dateTime: " + dateTime;
    }
}
