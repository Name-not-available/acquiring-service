package ru.demo.acquiringservice.object.operation;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("DepositOperation")
public class DepositOperation extends Operation {

  private Long operationHost;
  private BigDecimal depositAmount;
}
