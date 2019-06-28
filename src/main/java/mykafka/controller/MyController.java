package mykafka.controller;

import lombok.RequiredArgsConstructor;
import mykafka.service.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author suxiaolin
 * @date 2019-06-28 12:03
 */
@RestController
@RequestMapping("/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MyController {

    private final Producer producer;

    @RequestMapping("/test1")
    public String test1() {
        producer.send(String.format("my message currentTimeMillis: %d", System.currentTimeMillis()));
        return "test1";
    }
}
