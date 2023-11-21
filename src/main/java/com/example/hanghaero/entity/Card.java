package com.example.hanghaero.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "card")
@NoArgsConstructor
public class Card {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String description;

	@ColumnDefault("#FFFFFF")
	private String color;

	@OneToMany(mappedBy = "card")
	private List<User> userList = new ArrayList<>();

	@OneToMany(mappedBy = "card")
	private List<Comment> commentList = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "column_id")
	private Column column;

}
