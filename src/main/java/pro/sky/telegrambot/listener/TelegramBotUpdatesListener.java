package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.NotificationTask;
import pro.sky.telegrambot.exception.IncorrectCommandException;
import pro.sky.telegrambot.exception.NoWayToNotifyException;
import pro.sky.telegrambot.service.NotificationService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private NotificationService notificationService;

    @Autowired
    private TelegramBot telegramBot;

    public TelegramBotUpdatesListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            String messageText = update.message().text();
            logger.info("Processing update: {}", update);
            if(messageText.equals("/start")) {
                SendMessage message = new SendMessage(update.message().chat().id(), "Здорова, задолбал");
                telegramBot.execute(message);
            } else if(messageText.contains("/notify")) {
                SendMessage message;
                Long chatId = update.message().chat().id();
                try {
                    String date = notificationService.notifyCreator(messageText, chatId);
                    message = new SendMessage(chatId, "Уведомление создано. Я направлю вам сообщение: " + date);
                    telegramBot.execute(message);
                } catch (IncorrectCommandException e) {
                    message = new SendMessage(chatId, e.getMessage());
                    telegramBot.execute(message);
                } catch (NoWayToNotifyException e) {
                    message = new SendMessage(chatId, e.getMessage());
                    telegramBot.execute(message);
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
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
