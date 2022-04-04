package ru.demo.acquiringservice.processors;

import org.springframework.beans.factory.annotation.Autowired;
import ru.demo.acquiringservice.object.OperationType;
import ru.demo.acquiringservice.object.operation.Operation;
import ru.demo.acquiringservice.service.AcquiringService;

import java.math.BigDecimal;

public interface OperationProcessor {

  @Autowired
  default void registerProcessor(AcquiringService acquiringService) {
    acquiringService.register(this.getOperationType(), this);
  }

  BigDecimal doOperation(Operation operation);

  OperationType getOperationType();
}
