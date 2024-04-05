package com.zoi4erom.botmailing.repository.contract;

import com.zoi4erom.botmailing.entity.User;
import com.zoi4erom.botmailing.repository.Repository;
import java.util.Optional;

public interface UserRepository extends Repository<Integer, User> {
	Optional<User> getByUserId(String userId);
}