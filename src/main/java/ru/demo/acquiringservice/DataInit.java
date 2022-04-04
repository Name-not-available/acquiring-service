package ru.demo.acquiringservice;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.demo.acquiringservice.object.entity.Account;
import ru.demo.acquiringservice.restrepository.AccountRestRepository;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataInit implements ApplicationRunner {

  private final AccountRestRepository accountRepository;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    Account account = new Account();
    account.setBalance(BigDecimal.valueOf(52978.45));
    accountRepository.save(account);

    Account account2 = new Account();
    account2.setBalance(BigDecimal.valueOf(2.32));
    accountRepository.save(account2);

    Account account3 = new Account();
    account3.setBalance(BigDecimal.valueOf(88));
    accountRepository.save(account3);
  }
}

