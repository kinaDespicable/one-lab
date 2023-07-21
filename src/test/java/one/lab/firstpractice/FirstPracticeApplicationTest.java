package one.lab.firstpractice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
class FirstPracticeApplicationTest {

    private ApplicationService applicationService;

    @BeforeEach
    public void setup() {
        applicationService = new ApplicationService();
    }


    @Test
    void contextLoads() {
        FirstPracticeApplication.main(new String[]{});

        assertNotNull(applicationService, "ApplicationService bean should not be null");
    }
}

