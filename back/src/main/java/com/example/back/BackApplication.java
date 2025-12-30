package com.example.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class BackApplication {

	public static void main(String[] args) {
		// .env 파일 로드 후 시스템 환경 변수로 설정
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
		dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
        });
		// 애플리케이션 시작
		SpringApplication.run(BackApplication.class, args);
	}

}
