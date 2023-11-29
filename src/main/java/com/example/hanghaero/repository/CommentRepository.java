package com.example.hanghaero.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hanghaero.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findAllByCard_IdOrderByModifiedAtDesc(Long cardId);
}
