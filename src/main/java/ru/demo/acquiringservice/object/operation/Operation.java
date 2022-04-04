package ru.demo.acquiringservice.object.operation;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes(
    {
        @JsonSubTypes.Type(value = DepositOperation.class),
        @JsonSubTypes.Type(value = WithdrawOperation.class),
        @JsonSubTypes.Type(value = SendOperation.class)
    })
public abstract class Operation {

  public abstract Long getOperationHost();
}
