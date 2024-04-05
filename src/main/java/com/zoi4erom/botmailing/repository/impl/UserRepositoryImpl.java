package com.zoi4erom.botmailing.repository.impl;

import com.zoi4erom.botmailing.entity.User;
import com.zoi4erom.botmailing.repository.contract.UserRepository;
import com.zoi4erom.botmailing.util.HibernateUtil;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

	private final SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}


	@Override
	public void create(User user) {
		System.out.println("q");
		try (var session = getCurrentSession()) {
			session.beginTransaction();
			session.persist(user);
			session.getTransaction().commit();
		}
	}

	@Override
	public List<User> getAll() {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("FROM User", User.class).getResultList();
		}
	}

	@Override
	public Optional<User> getByUserId(String userId) {
		try (Session session = sessionFactory.openSession()) {
			User user = session.createQuery("FROM User WHERE userId = :userId", User.class)
			    .setParameter("userId", userId)
			    .getSingleResultOrNull();
			return Optional.ofNullable(user);
		}
	}
	@Override
	public User getById(Integer id) {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			User user = session.get(User.class, id);
			session.getTransaction().commit();
			return user;
		}
	}


	@Override
	public void update(User user) {
		@Cleanup var session = getCurrentSession();
		session.beginTransaction();
		session.merge(user);
		session.getTransaction().commit();
	}

	@Override
	public void delete(User user) {
		@Cleanup var session = getCurrentSession();
		session.beginTransaction();
		session.remove(user);
		session.getTransaction().commit();
	}
}
