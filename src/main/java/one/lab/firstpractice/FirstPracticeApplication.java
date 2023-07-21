package one.lab.firstpractice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@Slf4j
@SpringBootApplication
public class FirstPracticeApplication {

    public static void main(String[] args) {
        log.info("Application has been started at {}", LocalDateTime.now());
        SpringApplication.run(FirstPracticeApplication.class, args);
    }

    // Application automatically runs commandLineRunner bean from ApplicationService class.

}
