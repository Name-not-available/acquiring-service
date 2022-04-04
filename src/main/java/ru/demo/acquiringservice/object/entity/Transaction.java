package ru.demo.acquiringservice.object.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

  @Id
  @GeneratedValue
  private Long id;

  private BigDecimal sum;

  private BigDecimal currentBalance;

  @ManyToOne(targetEntity = Account.class)
  private Account account;

  public Transaction(BigDecimal sum, Account account, BigDecimal currentBalance) {
    this.sum = sum;
    this.account = account;
    this.currentBalance = currentBalance;
  }
}
