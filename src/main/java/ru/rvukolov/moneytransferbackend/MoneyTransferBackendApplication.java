package ru.rvukolov.moneytransferbackend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MoneyTransferBackendApplication {

    static Logger log = LoggerFactory.getLogger(MoneyTransferBackendApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(MoneyTransferBackendApplication.class, args);
        log.info("Application started");
    }

}
