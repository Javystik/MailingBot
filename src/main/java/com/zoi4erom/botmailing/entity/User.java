package com.zoi4erom.botmailing.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "USERS")
@Entity
@NoArgsConstructor
@Getter
@Setter
public class User implements BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "USER_NAME")
	private String name;
	@Column(name = "CREATED_AT")
	private LocalDateTime createdAt;

	public User(Long userId, String name, LocalDateTime createdAt) {
		this.userId = userId;
		this.name = name;
		this.createdAt = createdAt;
	}
}
