package com.example.hanghaero.fortest;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisServiceForTest {
	private final RedissonClient redissonClient;

	public void redisTest() {
		RLock lock = redissonClient.getLock("locktest");
		try {
			boolean available = lock.tryLock(10, 1, TimeUnit.SECONDS);
			if (!available) {
				System.out.println("lock timeout");
			}
			System.out.println("lock 내부 로직 시작");
			Thread.sleep(500);
			System.out.println("lock 내부 로직 완료");
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			lock.unlock();
		}
	}
}
