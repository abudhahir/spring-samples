package com.cleveloper.sihttp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class DummyController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DummyController.class);

    @GetMapping(value = "/foo",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> someMethod() {
        return ResponseEntity.ok().body("Yes it has reached");
    }

    @PostMapping(value = "/foo",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> someMethodPost() {
        return ResponseEntity.ok()
                .header("somehead", "someheadvalue")
                .body("Yes post has reached");
    }
}
