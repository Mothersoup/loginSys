package com.example.loginsystem.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration

/* * MyBatis配置类
 * 通过@MapperScan注解指定Mapper接口的包路径，
 * 这样MyBatis就可以扫描到这些接口并生成相应的实现类。
 * 注意：请根据实际项目的包结构修改@MapperScan注解中的路径，
 *       这里的路径是示例路径，请替换为实际的包名。
 *       一般情况下，Mapper接口会放在与Service层同级的包中，
 *       所以这里的路径应该是与Service层同级的包名。
 *
 * @MapperScan 注解用于指定 MyBatis Mapper 接口所在的包路径，
 *              这样 MyBatis 就可以自动扫描并注册这些接口。
 * github copilot 自動生成 。
 *
where interface location in this project
 */
@MapperScan("com.example.loginsystem.mapper")
public class MyBaitsConfig {

}
