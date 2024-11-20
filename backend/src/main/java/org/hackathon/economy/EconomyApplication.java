package org.hackathon.economy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // 스케줄링 기능 활성화
public class EconomyApplication {

	public static void main(String[] args) {
		SpringApplication.run(EconomyApplication.class, args);
	}
}
