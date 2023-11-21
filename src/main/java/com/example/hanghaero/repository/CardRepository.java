package com.example.hanghaero.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hanghaero.entity.Card;

public interface CardRepository extends JpaRepository<Card, Long> {
}
