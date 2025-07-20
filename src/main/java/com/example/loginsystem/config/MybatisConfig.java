package com.example.loginsystem.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.example.loginsystem.mapper") // 指定要變成實現類的接口所在的包
public class MybatisConfig {

}
