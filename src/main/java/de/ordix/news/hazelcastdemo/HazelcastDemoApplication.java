package de.ordix.news.hazelcastdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

@Slf4j
@SpringBootApplication
@EnableCaching
public class HazelcastDemoApplication implements CommandLineRunner {

	@Autowired
	DummyService dummyService;

	public static void main(String[] args) {
		SpringApplication.run(HazelcastDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		for (int i = 0; i < 5; i++) {
			log.info(dummyService.getLogMessage());
		}
	}

	@Service
	public class DummyService {
		@Cacheable("logMessage")
		public String getLogMessage() {
			log.info("METHOD: getLogMessage() hit");
			return "LOG-MESSAGE";
		}
	}
}
