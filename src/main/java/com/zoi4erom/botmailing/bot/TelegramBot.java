package com.zoi4erom.botmailing.bot;

import com.zoi4erom.botmailing.config.BotConfig;
import com.zoi4erom.botmailing.entity.User;
import com.zoi4erom.botmailing.exception.CustomTelegramException;
import com.zoi4erom.botmailing.service.impl.UserServiceImpl;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot{

	private final BotConfig botConfig;
	private final UserServiceImpl userService;

	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
			Message message = update.getMessage();
			String chatId = message.getChatId().toString();
			String text = message.getText();
			String userName = message.getFrom().getFirstName();

			if (text.equals("/start")) {
				String userId = String.valueOf(message.getFrom().getId());

				if (userService.getByUserId(userId).isEmpty()) {
					userService.createUser(Long.valueOf(userId), userName, LocalDateTime.now());
					sendMessage(chatId, "Ви зареєстровані!");
				} else {
					sendMessage(chatId, "Ви вже зареєстровані!");
				}
			}
			else {
				if (chatId.equals(String.valueOf(botConfig.getAdminId()))) {
					if (text.equals("/allUsers")) {
						List<User> allUsers = userService.getAllUsers();
						StringBuilder textToSend = new StringBuilder();
						for (User user : allUsers) {
							textToSend.append(user.getId()).append(": ")
							    .append(user.getUserId())
							    .append(" ").append(user.getName()).append(" ")
							    .append(user.getCreatedAt()).append("\n");
						}
						sendMessage(chatId, textToSend.toString());
					} else if (text.startsWith("/messageBroadcast")) {
						String[] parts = text.split("\\s+", 2);
						if (parts.length == 2) {
							String messageContent = parts[1];
							messageBroadcast(messageContent);
						} else {
							sendMessage(chatId, "Не введено текст повідомлення для розсилки.");
						}
					} else {
						sendMessage(chatId, "Не розпізнана команда. Для реєстрації натисніть /start, адміністратор може використовувати /allUsers або /messageBroadcast");
					}
				} else {
					sendMessage(chatId, "Не розпізнана команда. Для реєстрації натисніть /start");
				}
			}
		}
	}

	public void sendMessage(String chatId, String textToSend) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(chatId);
		sendMessage.setText(textToSend);
		try {
			execute(sendMessage);
		} catch (TelegramApiException e) {
			throw new CustomTelegramException("Сталася помилка під час виконання операції з Telegram API", e);
		}
	}

	public void messageBroadcast(String message) {
		int THREAD_POOL_SIZE = 10;
		try (ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE)) {
			List<User> allUsers = userService.getAllUsers();
			for (User user : allUsers) {
				Long userId = Long.valueOf(user.getUserId());
				executor.submit(() -> sendMessage(String.valueOf(userId), message));
			}
			executor.shutdown();
		} catch (Exception e) {
			throw new CustomTelegramException("Помилка під час розсилки повідомлення", e);
		}
	}

	public Long getAdminId() {
		return botConfig.getAdminId();
	}

	@Override
	public String getBotUsername() {
		return botConfig.getName();
	}

	public String getBotToken() {
		return botConfig.getApi();
	}
}
