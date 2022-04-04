package ru.demo.acquiringservice.object.operation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.demo.acquiringservice.object.OperationType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationRequest {

  private OperationType operationType;
  private Operation operationData;
}
