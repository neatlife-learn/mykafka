package mykafka;

import mykafka.service.Consumer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}")
@EmbeddedKafka
public class MyKafkaApplicationTests {

    private static final CountDownLatch latch = new CountDownLatch(1);

    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @Test
    public void testVanillaExchange() throws Exception {
        assertThat(latch.await(10, TimeUnit.SECONDS)).isTrue();
        assertThat(this.outputCapture.toString().contains("A simple test message"))
                .isTrue();
    }

    @TestConfiguration
    public static class Config {

        @Bean
        public Consumer consumer() {
            return new Consumer() {

                @Override
                public void processMessage(String message, List<Integer> partitions,
                                           List<String> topics, List<Long> offsets) {
                    super.processMessage("Test message", partitions, topics, offsets);
                    latch.countDown();
                }

            };
        }
    }
}
