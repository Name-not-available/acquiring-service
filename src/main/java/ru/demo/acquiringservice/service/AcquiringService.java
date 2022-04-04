package ru.demo.acquiringservice.service;

import java.util.EnumMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.demo.acquiringservice.object.OperationErrorList;
import ru.demo.acquiringservice.object.OperationType;
import ru.demo.acquiringservice.object.operation.OperationRequest;
import ru.demo.acquiringservice.object.operation.OperationResponse;
import ru.demo.acquiringservice.processors.OperationProcessor;

import java.math.BigDecimal;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AcquiringService {

  private final RedissonClient redissonClient;
  private final Map<OperationType, OperationProcessor> operationProcessors = new EnumMap<>(OperationType.class);

  public void register(OperationType operationType, OperationProcessor operationProcessor) {
    this.operationProcessors.put(operationType, operationProcessor);
  }

  @Transactional
  public OperationResponse processOperation(OperationRequest operationRequest) {
    OperationProcessor processor = operationProcessors.get(operationRequest.getOperationType());
    if (processor == null) {
      throw new UnsupportedOperationException(OperationErrorList.WRONG_OPERATION_TYPE.getError());
    }

    RLock lock = this.redissonClient.getFairLock(
        operationRequest.getOperationData().getOperationHost().toString());
    lock.lock();
    BigDecimal balance;
    try {
      balance = processor.doOperation(operationRequest.getOperationData());
    } finally {
      lock.unlock();
    }

    return new OperationResponse(200, "Операция выполнена", balance);
  }
}
