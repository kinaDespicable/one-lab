package one.lab.firstpractice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaListeners {

    @KafkaListener(topics = {"Example_topic", "News"}, groupId = "groupId")
    void listener(String data){
        log.info("Listener received data: " + data);
    }

}
