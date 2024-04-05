package com.zoi4erom.botmailing;

import com.zoi4erom.botmailing.bot.BotRunner;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context =
		    new AnnotationConfigApplicationContext("com.zoi4erom.botmailing");

		BotRunner botRunner = context.getBean("botRunner", BotRunner.class);
		botRunner.start();

		context.close();
	}
}
