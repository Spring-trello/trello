package com.example.hanghaero.repository;

import org.hibernate.annotations.Columns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.hanghaero.entity.Col;

@Repository
public interface ColRepository extends JpaRepository<Col, Long> {
	@Query(value = "select * from columns where position=? ",nativeQuery = true)
	Col getPosition(int position);

	@Query(value = "select position from columns where board_id=? order By position DESC limit 1", nativeQuery = true)
	Integer lastPosition(Long boardId);
}
