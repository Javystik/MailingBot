package com.zoi4erom.botmailing.service.impl;

import com.zoi4erom.botmailing.entity.User;
import com.zoi4erom.botmailing.repository.impl.UserRepositoryImpl;
import com.zoi4erom.botmailing.service.contract.UserService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepositoryImpl userRepository;

	@Override
	public void createUser(Long id, String name, LocalDateTime createdAt) {
		userRepository.create(new User(id, name, createdAt));
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.getAll();
	}

	@Override
	public Optional<User> getByUserId(String userId) {
		return userRepository.getByUserId(userId);
	}
}
