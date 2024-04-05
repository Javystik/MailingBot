package com.zoi4erom.botmailing.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.zoi4erom.botmailing")
@PropertySource("classpath:application.context")
@Getter
public class BotConfig {

	@Value("${bot.name}")
	private String name = "mailingzoi4erom_bot";
	@Value("${bot.api}")
	private String api;
	@Value("${bot.admin.id}")
	private Long adminId;
}
