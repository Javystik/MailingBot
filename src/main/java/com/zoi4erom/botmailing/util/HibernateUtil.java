package com.zoi4erom.botmailing.util;

import com.zoi4erom.botmailing.entity.User;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Getter
public class HibernateUtil {

	private static final HibernateUtil INSTANCE = new HibernateUtil();

	private HibernateUtil() {
	}

	public static HibernateUtil getInstance() {
		return INSTANCE;
	}

	private final SessionFactory sessionFactory = buildSessionFactory();

	private SessionFactory buildSessionFactory() {
		Configuration configuration = new Configuration();
		configuration.configure();
		configuration.addAnnotatedClass(User.class);
		return configuration.buildSessionFactory();
	}
}
