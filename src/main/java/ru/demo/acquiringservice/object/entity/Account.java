package ru.demo.acquiringservice.object.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
public class Account {

  @Id
  @GeneratedValue
  private Long id;

  private BigDecimal balance;
}
