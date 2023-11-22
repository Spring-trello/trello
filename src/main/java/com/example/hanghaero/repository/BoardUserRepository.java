package com.example.hanghaero.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hanghaero.entity.BoardUser;

public interface BoardUserRepository extends JpaRepository<BoardUser, Long> {
	BoardUser findByBoardId(Long id);
}
