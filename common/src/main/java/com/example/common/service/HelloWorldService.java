package com.example.common.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;

@Log
@Getter
@Setter
@NoArgsConstructor
public class HelloWorldService {

    private String externalMessage;

    public HelloWorldService(String initialMessage) {
        this.externalMessage = initialMessage;
    }

    public void hello(String message) {
        log.info(message);
        log.info(externalMessage);
    }
}
