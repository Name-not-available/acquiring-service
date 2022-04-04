package ru.demo.acquiringservice.object.operation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationResponse {

  private int status;
  private String statusText;
  private BigDecimal balance;
}
