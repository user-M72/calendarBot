package calendarbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CalendarBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalendarBotApplication.class, args);
	}

}
