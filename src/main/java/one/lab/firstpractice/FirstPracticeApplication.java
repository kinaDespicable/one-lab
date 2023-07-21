package one.lab.firstpractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FirstPracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirstPracticeApplication.class, args);
    }

    // Application automatically runs commandLineRunner bean from ApplicationService class.

}
