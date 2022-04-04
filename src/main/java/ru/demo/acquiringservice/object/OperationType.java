package ru.demo.acquiringservice.object;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OperationType {
  DEPOSIT("DEPOSIT"),
  WITHDRAW("WITHDRAW"),
  SEND("SEND");

  private final String value;
}
