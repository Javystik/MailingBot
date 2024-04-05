package com.zoi4erom.botmailing.repository;

import com.zoi4erom.botmailing.entity.BaseEntity;
import com.zoi4erom.botmailing.entity.User;
import java.util.List;

public interface Repository<K, E extends BaseEntity> {
	void create(E e);
	List<E> getAll();
	User getById(K id);
	void update(E e);
	void delete(E e);
}
