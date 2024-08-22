package pro.sky.telegrambot.scheduler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.entity.NotificationTask;
import pro.sky.telegrambot.service.NotificationService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class Scheduler {


    private TelegramBot telegramBot;
    private NotificationService notificationService;

    public Scheduler(TelegramBot telegramBot, NotificationService notificationService) {
        this.telegramBot = telegramBot;
        this.notificationService = notificationService;
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void notificationSender() {
        List<NotificationTask> tasks = notificationService.getNotifiesByDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        if (!tasks.isEmpty()) {
            for (NotificationTask task: tasks) {
                SendMessage message = new SendMessage(task.getChatId(), "Вы просили напомнить меня о следующем: " + task.getNotificationMessage());
                telegramBot.execute(message);
            }
        }
    }

}
