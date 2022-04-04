package ru.demo.acquiringservice.processors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.demo.acquiringservice.object.OperationErrorList;
import ru.demo.acquiringservice.object.entity.Account;
import ru.demo.acquiringservice.object.OperationType;
import ru.demo.acquiringservice.object.entity.Transaction;
import ru.demo.acquiringservice.object.operation.Operation;
import ru.demo.acquiringservice.object.operation.WithdrawOperation;
import ru.demo.acquiringservice.restrepository.AccountRestRepository;
import ru.demo.acquiringservice.restrepository.TransactionRestRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WithdrawOperationProcessor implements OperationProcessor {

  private final TransactionRestRepository transactionRepository;
  private final AccountRestRepository accountRepository;

  @Override
  public OperationType getOperationType() {
    return OperationType.WITHDRAW;
  }

  private void validateTransaction(WithdrawOperation withdrawOperation) {
    if (withdrawOperation.getWithdrawAmount().compareTo(BigDecimal.ZERO) < 0) {
      throw new UnsupportedOperationException(OperationErrorList.WRONG_WITHDRAW_AMOUNT.getError());
    }
  }

  private BigDecimal processTransaction(WithdrawOperation withdrawOperation) {
    Account account = accountRepository.findById(withdrawOperation.getOperationHost())
        .orElseThrow(); // todo add exception
    BigDecimal balanceAfterOperation = account.getBalance()
        .subtract(withdrawOperation.getWithdrawAmount());
    if (balanceAfterOperation.compareTo(BigDecimal.ZERO) < 0) {
      throw new UnsupportedOperationException(OperationErrorList.WRONG_FINAL_SUM.getError());
    }

    Transaction transaction = new Transaction(withdrawOperation.getWithdrawAmount().negate(),
        account, balanceAfterOperation);
    account.setBalance(balanceAfterOperation);
    transactionRepository.save(transaction);
    accountRepository.save(account);

    return balanceAfterOperation;
  }

  @Override
  public BigDecimal doOperation(Operation operation) {
    WithdrawOperation withdrawOperation = (WithdrawOperation) operation;

    this.validateTransaction(withdrawOperation);

    return this.processTransaction(withdrawOperation);
  }
}
