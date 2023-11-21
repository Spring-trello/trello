package com.example.hanghaero.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.hanghaero.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
