package pro.sky.telegrambot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    private NotificationService notificationService;
    private List<NotificationTask> testTasks;

    @Mock
    private NotificationTaskRepository notificationTaskRepository;

    @BeforeEach
    public void setUp() {
        notificationService = new NotificationService(notificationTaskRepository);
    }

    @BeforeEach
    public void reposInit() {
        testTasks.add(new NotificationTask(0L, "Пей таблетки",222L, LocalDateTime.parse("01.01.2022 20:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))));
        testTasks.add(new NotificationTask(0L, "Ломай Пентагон",222L, LocalDateTime.parse("01.01.2021 20:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))));
    }

    @Test
    public void notifyCreator() {

    }

    @Test
    public void saveNotify() {

    }

    @Test
    public void getNotifiesByDate() {

    }

}
