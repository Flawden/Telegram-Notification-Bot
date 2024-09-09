package pro.sky.telegrambot.service;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
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
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    private NotificationService notificationService;
    private List<NotificationTask> testTasks = new ArrayList<>();

    @Mock
    private NotificationTaskRepository notificationTaskRepository;

    @BeforeEach
    public void setUp() {
        notificationService = new NotificationService(notificationTaskRepository);
    }

    @BeforeEach
    public void reposInit() {
        testTasks.add(new NotificationTask(0L, "Пей таблетки",222L, LocalDateTime.parse("01.01.2022 20:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))));
        testTasks.add(new NotificationTask(0L, "Ломай Пентагон",322L, LocalDateTime.parse("01.01.2021 20:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))));
        testTasks.add(new NotificationTask(0L, "Дружи, свисти",222L, LocalDateTime.parse("02.01.2013 20:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))));
    }

    @Test
    public void notifyCreator() {
        String testMessage = "/notify 19.08.9024 23:00 Погулять с другом";
        Long chatId = 241L;
        NotificationTask testNotification = new NotificationTask(0L, testMessage.substring(25), chatId, LocalDateTime.parse(testMessage.substring(8, 24), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        Assertions.assertEquals(testMessage.substring(8, 24), notificationService.notifyCreator(testMessage, chatId));
    }

    @Test
    public void notifyCreatorWithNoWayToNotifyException() {
        Long charId = 222L;
        LocalDateTime localDateTime = LocalDateTime.now().minusYears(1);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String timeBeforeNow = dateTimeFormatter.format(localDateTime);
        String testMessage = "/notify " + timeBeforeNow + " Погулять с другом";
        NoWayToNotifyException exception = Assertions.assertThrows(NoWayToNotifyException.class, () -> notificationService.notifyCreator(testMessage, charId));
        Assertions.assertEquals("Ошибка! Невозможно отправить сообщение в прошлое. Если бы я умел, я бы не был простым ботом.", exception.getMessage());
    }

    @Test
    public void notifyCreatorWithIncorrectCommandException() {
        Long charId = 222L;
        String testMessage = "/notify 123 Погулять с другом";
        IncorrectCommandException exception = Assertions.assertThrows(IncorrectCommandException.class, () -> notificationService.notifyCreator(testMessage, charId));
        Assertions.assertEquals("Ошибка! Команда должна быть выглядеть следующим образом: \"/nofity дд.мм.гггг чч:мм Напоминание\"", exception.getMessage());
    }

    @Test
    public void saveNotify() {
        when(notificationService.saveNotify(testTasks.get(0))).thenReturn(testTasks.get(0));
        Assertions.assertEquals(testTasks.get(0), notificationService.saveNotify(testTasks.get(0)));
    }

    @Test
    public void getNotifiesByDate() {
        List testList = new ArrayList();
        testList.add(testTasks.get(0));
        when(notificationService.getNotifiesByDate(testTasks.get(0).getDateTime())).thenReturn(testList);
        Assertions.assertEquals(testList, notificationService.getNotifiesByDate(testTasks.get(0).getDateTime()));
    }

    @Test
    public void getNotifiesByDateAnotherOne() {
        List testList = new ArrayList();
        testList.add(testTasks.get(0));
        when(notificationService.getNotifiesByDate(testTasks.get(0).getDateTime())).thenReturn(testList);
        Assertions.assertEquals(testList, notificationService.getNotifiesByDate(testTasks.get(0).getDateTime()));
    }

}
