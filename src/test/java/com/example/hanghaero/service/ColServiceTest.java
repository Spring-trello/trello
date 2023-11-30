// package com.example.hanghaero.service;
//
// import static org.assertj.core.api.Assertions.*;
//
// import java.util.concurrent.ExecutionException;
// import java.util.concurrent.ExecutorService;
// import java.util.concurrent.Executors;
// import java.util.concurrent.Future;
//
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.context.annotation.Profile;
// import org.springframework.dao.OptimisticLockingFailureException;
//
// import com.example.hanghaero.dto.column.ColModifyRequestDto;
// import com.example.hanghaero.entity.Col;
// import com.example.hanghaero.repository.ColRepository;
//
// @SpringBootTest
// @Profile("deploy")
// public class ColServiceTest {
// 	@Autowired
// 	ColService columnService;
//
// 	@Autowired
// 	ColRepository columnRepository;
//
// 	private ColModifyRequestDto createColumnModifyRequestDto(int number) {
// 		return ColModifyRequestDto.builder()
// 			.title("수정" + number)
// 			.boardId(3L)
// 			.build();
// 	}
//
// 	@Test
// 	void modifyColumnThrowsFailureExceptionTest() {
//
// 		//given
// 		int totalRequest = 10;
// 		ExecutorService executorService = Executors.newFixedThreadPool(totalRequest);
//
// 		ColModifyRequestDto[] dtos = new ColModifyRequestDto[totalRequest];
// 		for (int i = 0; i < totalRequest; i++) {
// 			dtos[i] = createColumnModifyRequestDto(i + 1);
// 		}
//
// 		Future<?>[] futures = new Future[totalRequest];
// 		Exception exception = new Exception();
//
// 		for (int i = 0; i < totalRequest; i++) {
// 			int ii = i;
// 			futures[ii] = executorService.submit(() -> columnService.updateColumn(45L, dtos[ii]));
// 		}
//
// 		try {
// 			for (int i = 0; i < totalRequest; i++) {
// 				futures[i].get();
// 			}
// 		} catch (ExecutionException | InterruptedException e) {
// 			exception = (Exception)e.getCause();
// 		}
// 		// 위 작업중 Execution Exception이 발생했고 OptimisticLock 에 의한 수정 불가 예외가 발생한 경우 OptimisticLockingFailureException를 던짐
// 		assertThat(exception).isInstanceOf(OptimisticLockingFailureException.class);
//
// 		Col lastChangedColumn = columnRepository.findByColumnId(45L).orElseThrow();
// 		// 항상 다르다.
// 		System.out.println(lastChangedColumn.getTitle());
// 	}
// }
