package ru.demo.acquiringservice.object.operation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawOperation extends Operation {

  private Long operationHost;
  private BigDecimal withdrawAmount;
}
