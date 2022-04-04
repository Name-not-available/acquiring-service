package ru.demo.acquiringservice.processors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.demo.acquiringservice.object.OperationErrorList;
import ru.demo.acquiringservice.object.entity.Account;
import ru.demo.acquiringservice.object.OperationType;
import ru.demo.acquiringservice.object.entity.Transaction;
import ru.demo.acquiringservice.object.operation.Operation;
import ru.demo.acquiringservice.object.operation.SendOperation;
import ru.demo.acquiringservice.restrepository.AccountRestRepository;
import ru.demo.acquiringservice.restrepository.TransactionRestRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class SendOperationProcessor implements OperationProcessor {

  private final TransactionRestRepository transactionRepository;
  private final AccountRestRepository accountRepository;

  @Override
  public OperationType getOperationType() {
    return OperationType.SEND;
  }

  private void validateTransaction(SendOperation sendOperation) {
    if (sendOperation.getSendAmount().compareTo(BigDecimal.ZERO) < 0) {
      throw new UnsupportedOperationException(OperationErrorList.WRONG_SEND_AMOUNT.getError());
    }

    if (sendOperation.getOperationHost().equals(sendOperation.getOperationReceiver())) {
      throw new UnsupportedOperationException(OperationErrorList.WRONG_RECEIVER_ID.getError());
    }
  }

  private BigDecimal processSenderTransaction(SendOperation sendOperation) {
    Account senderAccount = accountRepository.findById(sendOperation.getOperationHost())
        .orElseThrow(); // todo add custom exception
    BigDecimal senderBalanceAfterOperation = senderAccount.getBalance()
        .subtract(sendOperation.getSendAmount());
    if (senderBalanceAfterOperation.compareTo(BigDecimal.ZERO) < 0) {
      throw new UnsupportedOperationException(OperationErrorList.WRONG_FINAL_SUM.getError());
    }
    senderAccount.setBalance(senderBalanceAfterOperation);
    accountRepository.save(senderAccount);
    Transaction senderTransaction = new Transaction(sendOperation.getSendAmount().negate(),
        senderAccount, senderBalanceAfterOperation);
    transactionRepository.save(senderTransaction);

    return senderBalanceAfterOperation;
  }

  private void processReceiverTransaction(SendOperation sendOperation) {
    Account receiverAccount = accountRepository.findById(sendOperation.getOperationReceiver())
        .orElseThrow(); // todo add custom exception
    BigDecimal receiverBalanceAfterOperation = receiverAccount.getBalance()
        .add(sendOperation.getSendAmount());
    receiverAccount.setBalance(receiverBalanceAfterOperation);
    accountRepository.save(receiverAccount);
    Transaction receiverTransaction = new Transaction(sendOperation.getSendAmount(),
        receiverAccount, receiverBalanceAfterOperation);
    transactionRepository.save(receiverTransaction);
  }

  @Override
  public BigDecimal doOperation(Operation operation) {
    SendOperation sendOperation = (SendOperation) operation;

    this.validateTransaction(sendOperation);
    BigDecimal senderBalanceAfterOperation = this.processSenderTransaction(sendOperation);
    this.processReceiverTransaction(sendOperation);

    return senderBalanceAfterOperation;
  }
}
