package com.example.loginsystem.config;

<<<<<<< HEAD
=======

>>>>>>> 44cc6f89f907b34ea580ca5525ff27e69c424180
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
<<<<<<< HEAD
import org.springframework.data.redis.serializer.RedisSerializer;

import java.io.Serializable;

@Configuration
public class RedisConfig  {
    @Bean
    /**
     * RedisTemplate 用於操作 Redis 數據庫 and 數據庫的連線操作
     * @param redisConnectionFactory Redis 連接工廠
     * @return RedisTemplate 實例
     */
    public RedisTemplate<String, Serializable> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();

        //redis 連接
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        //序列化器 - 對象序列化才能在網路傳輸(怎麼在網路傳輸)
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(RedisSerializer.json());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setHashValueSerializer(RedisSerializer.json());

        return redisTemplate;
    }






=======
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

>>>>>>> 44cc6f89f907b34ea580ca5525ff27e69c424180
}
