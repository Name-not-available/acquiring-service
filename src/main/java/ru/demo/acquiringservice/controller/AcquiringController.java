package ru.demo.acquiringservice.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.demo.acquiringservice.object.operation.OperationRequest;
import ru.demo.acquiringservice.object.operation.OperationResponse;
import ru.demo.acquiringservice.service.AcquiringService;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AcquiringController {

  private final AcquiringService acquiringService;

  @PostMapping("/operation")
  private OperationResponse postDeposit(@RequestBody OperationRequest operationRequest) {
    return acquiringService.processOperation(operationRequest);
  }
}
