package com.example.hanghaero.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hanghaero.entity.Card;

import jakarta.persistence.LockModeType;

public interface CardRepository extends JpaRepository<Card, Long> {
	@Query("SELECT c FROM Card c WHERE c.board.id = :boardId")
	List<Card> getCards(@Param("boardId") Long boardId);

	@Lock(LockModeType.OPTIMISTIC)
	Optional<Card> findWithOptimisticLockById(Long id);

	Optional<Card> findFirstByColumn_ColumnIdOrderByPositionDesc(Long columnId);

	// 카드를 다른 컬럼으로 옮길 때 포지션이 변경되는 카드목록 반환
	List<Card> findAllByPositionGreaterThanAndColumn_ColumnId(int pos, Long columnId);

	// 컬럼 내부에서 카드의 순서를 바꿀 때 포지션이 변경되는 카드모록 반환
	List<Card> findAllByPositionGreaterThanEqualAndColumn_ColumnId(int pos, Long columnId);

	@Query("SELECT c FROM Card c WHERE c.column.columnId = :columnId ORDER BY c.position ASC")
	List<Card> getCardsByColumnId(@Param("columnId") Long columnId);
}
