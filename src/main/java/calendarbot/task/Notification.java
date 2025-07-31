package calendarbot.task;

import org.springframework.scheduling.annotation.Scheduled;

public class Notification {

    @Scheduled(cron = "0 0 9 * * * ")
    public void sendNotification(){

    }

    @Scheduled(cron = "0 0/30 * * * ")
    public void sendNotificationEachHalfHour(){

    }
}
