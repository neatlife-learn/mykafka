package mykafka.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class Consumer {

    @KafkaListener(topics = "mytopic", groupId = "myconsumergroup")
    public void processMessage(String message,
                               @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                               @Header(KafkaHeaders.RECEIVED_TOPIC) List<String> topics,
                               @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.info(
                "received message, topic: {}, partition: {}, offset: {}, message: {}",
                topics.get(0),
                partitions.get(0),
                offsets.get(0),
                message
        );
    }

}
