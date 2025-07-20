package com.example.loginsystem;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.util.AssertionErrors.*;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisConnectionFactory connectionFactory; // 注入連線工廠，用於測試連線狀態

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate; // 注入 RedisTemplate，用於執行操作

    @Autowired
    private StringRedisTemplate stringRedisTemplate; // 專門用於 String 類型，更方便

    private final String TEST_KEY = "test:myKey";
    private final String TEST_VALUE = "Hello Redis Test!";
    private final String TEST_EXPIRATION_KEY = "test:expiringKey";
    private final String TEST_EXPIRATION_VALUE = "This will expire soon!";

    // --- 測試前準備與測試後清理 ---

    @BeforeEach
    void setUp() {
        // 在每個測試開始前，確保測試鍵不存在，清理環境
        stringRedisTemplate.delete(TEST_KEY);
        stringRedisTemplate.delete(TEST_EXPIRATION_KEY);
    }

    @AfterEach
    void tearDown() {
        // 在每個測試結束後，再次清理測試鍵，確保不影響其他測試
        stringRedisTemplate.delete(TEST_KEY);
        stringRedisTemplate.delete(TEST_EXPIRATION_KEY);
    }

    // --- 測試：Redis 連線 ---

    @Test
    void testRedisConnection() {
        // 嘗試獲取一個 Redis 連線，如果不拋出異常，表示連線成功
        try (RedisConnection connection = connectionFactory.getConnection()) {
            assertNotNull(connection, "Redis 連線應該不為空");
            // 執行一個 PING 命令，確認 Redis 伺服器活躍
            assertTrue(connection.ping(), true);
            System.out.println("Successfully connected to Redis!");
        } catch (Exception e) {
            fail("Redis 連線失敗: " + e.getMessage());
        }
    }

    // --- 測試：基本資料類型 - String (CRUD) ---

    @Test
    void testStringSetAndGet() {
        // Create (建立)
        stringRedisTemplate.opsForValue().set(TEST_KEY, TEST_VALUE);
        System.out.println("Set key: " + TEST_KEY + ", value: " + TEST_VALUE);

        // Read (讀取)
        String retrievedValue = stringRedisTemplate.opsForValue().get(TEST_KEY);
        assertNotNull(retrievedValue, "讀取到的值不應為空");
        assertEquals(TEST_VALUE, retrievedValue, retrievedValue);
        System.out.println("Get key: " + TEST_KEY + ", value: " + retrievedValue);
    }

    @Test
    void testStringUpdate() {
        stringRedisTemplate.opsForValue().set(TEST_KEY, TEST_VALUE); // 設定初始值

        // Update (更新)
        String updatedValue = "Updated Redis Value!";
        stringRedisTemplate.opsForValue().set(TEST_KEY, updatedValue);
        System.out.println("Update key: " + TEST_KEY + ", new value: " + updatedValue);

        String retrievedValue = stringRedisTemplate.opsForValue().get(TEST_KEY);
        assertEquals(updatedValue, retrievedValue, updatedValue);
    }

    @Test
    void testStringDelete() {
        stringRedisTemplate.opsForValue().set(TEST_KEY, TEST_VALUE); // 設定初始值

        // Delete (刪除)
        Boolean deleted = stringRedisTemplate.delete(TEST_KEY);
        assertTrue("刪除操作應該成功", Boolean.TRUE.equals(deleted));
        System.out.println("Deleted key: " + TEST_KEY);

        String retrievedValue = stringRedisTemplate.opsForValue().get(TEST_KEY);
        assertNull("should be null", retrievedValue);
    }

    // --- 測試：帶有過期時間的鍵 ---

    @Test
    void testStringWithExpiration() throws InterruptedException {
        // 設定一個帶有短暫過期時間的鍵 (例如 2 秒)
        stringRedisTemplate.opsForValue().set(TEST_EXPIRATION_KEY, TEST_EXPIRATION_VALUE, 2, TimeUnit.SECONDS);
        System.out.println("Set key: " + TEST_EXPIRATION_KEY + " with 2 seconds expiration.");

        // 立即讀取，應該能讀到
        String valueBeforeExpiration = stringRedisTemplate.opsForValue().get(TEST_EXPIRATION_KEY);
        assertNotNull(valueBeforeExpiration,"有值" );
        assertEquals("過期前讀取的值應該與設定的值相同", valueBeforeExpiration, TEST_EXPIRATION_VALUE);

        // 等待超過過期時間
        TimeUnit.SECONDS.sleep(3); // 等待 3 秒

        // 再次讀取，應該為空 (已過期)
        String valueAfterExpiration = stringRedisTemplate.opsForValue().get(TEST_EXPIRATION_KEY);
        assertNull("過期後的值應該為空", valueAfterExpiration);
        System.out.println("Key: " + TEST_EXPIRATION_KEY + " has expired.");
    }
}
