package ru.demo.acquiringservice.processors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.demo.acquiringservice.object.OperationErrorList;
import ru.demo.acquiringservice.object.entity.Account;
import ru.demo.acquiringservice.object.OperationType;
import ru.demo.acquiringservice.object.entity.Transaction;
import ru.demo.acquiringservice.object.operation.DepositOperation;
import ru.demo.acquiringservice.object.operation.Operation;
import ru.demo.acquiringservice.restrepository.AccountRestRepository;
import ru.demo.acquiringservice.restrepository.TransactionRestRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DepositOperationProcessor implements OperationProcessor {

  private final TransactionRestRepository transactionRepository;
  private final AccountRestRepository accountRepository;

  @Override
  public OperationType getOperationType() {
    return OperationType.DEPOSIT;
  }

  private void validateTransaction(DepositOperation depositOperation) {
    if (depositOperation.getDepositAmount().compareTo(BigDecimal.ZERO) < 0) {
      throw new UnsupportedOperationException(OperationErrorList.WRONG_DEPOSIT_AMOUNT.getError());
    }
  }

  private BigDecimal processSenderTransaction(DepositOperation depositOperation) {
    Account account = accountRepository.findById(depositOperation.getOperationHost())
        .orElseThrow(); // todo add exception
    BigDecimal balanceAfterOperation = account.getBalance()
        .add(depositOperation.getDepositAmount());
    if (balanceAfterOperation.compareTo(BigDecimal.ZERO) < 0) {
      throw new UnsupportedOperationException(OperationErrorList.WRONG_FINAL_SUM.getError());
    }

    Transaction transaction = new Transaction(depositOperation.getDepositAmount(), account,
        balanceAfterOperation);
    account.setBalance(balanceAfterOperation);
    transactionRepository.save(transaction);
    accountRepository.save(account);

    return balanceAfterOperation;
  }


  @Override
  public BigDecimal doOperation(Operation operation) {
    DepositOperation depositOperation = (DepositOperation) operation;

    this.validateTransaction(depositOperation);

    return this.processSenderTransaction(depositOperation);
  }
}
