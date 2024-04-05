package com.zoi4erom.botmailing.service.contract;

import com.zoi4erom.botmailing.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserService {
	void createUser(Long id, String name, LocalDateTime createdAt);
	List<User> getAllUsers();
	Optional<User> getByUserId(String userId);
}
