package ru.demo.acquiringservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class AcquiringServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(AcquiringServiceApplication.class, args);
  }

}
