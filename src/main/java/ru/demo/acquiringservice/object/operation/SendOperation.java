package ru.demo.acquiringservice.object.operation;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class SendOperation extends Operation {

  private Long operationHost;
  private Long operationReceiver;
  private BigDecimal sendAmount;
}
