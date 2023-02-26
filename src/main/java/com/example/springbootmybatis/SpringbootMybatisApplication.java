//openApi: http://localhost:8080/swagger-ui.html
package com.example.springbootmybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(RedisConfiguration.class) // 加载Redis配置
@MapperScan("com.example.springbootmybatis.mapper")
public class SpringbootMybatisApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringbootMybatisApplication.class, args);
	}
}
