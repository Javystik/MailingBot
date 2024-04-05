package com.zoi4erom.botmailing.exception;

public class CustomTelegramException extends RuntimeException{
	public CustomTelegramException(String message) {
		super(message);
	}

	public CustomTelegramException(String message, Throwable cause) {
		super(message, cause);
	}
}
