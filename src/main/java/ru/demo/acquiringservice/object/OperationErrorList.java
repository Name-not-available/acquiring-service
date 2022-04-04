package ru.demo.acquiringservice.object;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OperationErrorList {
  WRONG_OPERATION_TYPE("Передан неподдерживаемый тип операции"),
  WRONG_DEPOSIT_AMOUNT("Сумма пополнения не может быть отрицательной"),
  WRONG_FINAL_SUM("Баланс счета станет отрицательным"),
  WRONG_SEND_AMOUNT("Сумма перевода не может быть отрицательной"),
  WRONG_RECEIVER_ID("Отправитель и получатель должны быть разными"),
  WRONG_WITHDRAW_AMOUNT("Сумма снятия не может быть отрицательной");

  private final static String ERROR_PREFIX = "Невозможно выполнить операцию: ";
  
  private final String error;

  public String getError() {
    return ERROR_PREFIX + error;
  }
}
