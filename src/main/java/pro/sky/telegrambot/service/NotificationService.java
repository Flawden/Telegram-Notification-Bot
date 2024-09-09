package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.NotificationTask;
import pro.sky.telegrambot.exception.IncorrectCommandException;
import pro.sky.telegrambot.exception.NoWayToNotifyException;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NotificationService {

    NotificationTaskRepository notificationTaskRepository;

    public NotificationService(NotificationTaskRepository notificationTaskRepository) {
        this.notificationTaskRepository = notificationTaskRepository;
    }

    public String notifyCreator(String message, Long chatId) {
        Pattern pattern = Pattern.compile("(\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2})(\\s+)(.+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            String dateText = message.substring(8, 24);
            LocalDateTime date = LocalDateTime.parse(dateText, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
            if(date.isBefore(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))) {
                throw new NoWayToNotifyException("Ошибка! Невозможно отправить сообщение в прошлое. Если бы я умел, я бы не был простым ботом.");
            }
            NotificationTask notification = new NotificationTask(chatId, message.substring(25), date);
            saveNotify(notification);
            return dateText;
        } else {
            throw new IncorrectCommandException("Ошибка! Команда должна быть выглядеть следующим образом: \"/nofity дд.мм.гггг чч:мм Напоминание\"");
        }
    }

    public NotificationTask saveNotify(NotificationTask notificationTask) {
        return notificationTaskRepository.save(notificationTask);
    }

    public List<NotificationTask> getNotifiesByDate(String dateText) {
        LocalDateTime date = LocalDateTime.parse(dateText, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        return notificationTaskRepository.findNotificationTasksByDateTime(date);
    }

    public List<NotificationTask> getNotifiesByDate(LocalDateTime date) {
        return notificationTaskRepository.findNotificationTasksByDateTime(date);
    }


}
