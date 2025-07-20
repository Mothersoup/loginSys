package com.example.loginsystem;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class SpringBootRedisConfigTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void testRedisConnection() {
        // 1. 准备测试数据
        String testKey = "test:redis:connection:key";
        String testValue = "Hello Redis";

        try {
            // 2. 写入数据
            redisTemplate.opsForValue().set(testKey, testValue);

            // 3. 读取数据
            String retrievedValue = redisTemplate.opsForValue().get(testKey);

            // 4. 验证结果
            assertNotNull(retrievedValue, "not should be null");
            assertEquals(testValue, retrievedValue, "write and read should be equal ");

        } finally {
            redisTemplate.delete(testKey);
        }
    }
}