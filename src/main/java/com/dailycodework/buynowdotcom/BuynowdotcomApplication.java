package com.dailycodework.buynowdotcom;

import com.dailycodework.buynowdotcom.service.message.MessageService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BuynowdotcomApplication {

	public static void main(String[] args) {
		// MessageService messageService = new MessageService();
		// messageService.sendSMS();
		SpringApplication.run(BuynowdotcomApplication.class, args);
	}

	 @Bean
    public CommandLineRunner demo(MessageService messageService) {
        return args -> {
			//MessageService messageService = new MessageService();
            messageService.sendSMS(); // now Spring has injected values
        };
    }

}
