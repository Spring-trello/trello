// package com.example.hanghaero;
//
// import java.util.concurrent.CountDownLatch;
// import java.util.concurrent.ExecutorService;
// import java.util.concurrent.Executors;
//
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.context.annotation.Profile;
//
// import com.example.hanghaero.fortest.RedisServiceForTest;
//
// @SpringBootTest
// @Profile("local")
// public class RedisTest {
//
// 	@Autowired
// 	RedisServiceForTest redisService;
//
// 	@Test
// 	@DisplayName("트랜잭션")
// 	void lockBeforeTransaction() throws InterruptedException {
// 		//given
// 		ExecutorService executorService = Executors.newFixedThreadPool(5);
// 		CountDownLatch countDownLatch = new CountDownLatch(5);
//
// 		//when
// 		for (int i = 0; i < 10; i++) {
// 			executorService.submit(() -> {
// 				try {
// 					redisService.redisTest();
// 				} finally {
// 					countDownLatch.countDown();
// 				}
// 			});
// 		}
// 		countDownLatch.await();
//
// 		//then
// 		assert true;
// 	}
// }
