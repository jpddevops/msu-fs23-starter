package com.evolutiops.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("otel/v1/appone")
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);
    private final RestTemplate restTemplate;

    @Autowired
    public TestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> testOtelInstrumentation() {
        LOGGER.info("testing OTel tracing!");
        try {
            return restTemplate.getForEntity("http://otel-app-two:8081/otel/v1/apptwo/test", String.class);
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

}
