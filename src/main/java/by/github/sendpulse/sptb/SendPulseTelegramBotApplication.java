package by.github.sendpulse.sptb;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SendPulseTelegramBotApplication {

	private static final Logger log = Logger.getLogger(SendPulseTelegramBotApplication.class);

	public static void main(String[] args) {
		log.info("Application has started");
		SpringApplication.run(SendPulseTelegramBotApplication.class, args);
	}

}
