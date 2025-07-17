package com.membership.exception;

public class OperationFailedException extends BusinessException {
   public OperationFailedException(String operation) {
      super(ErrorCode.OPERATION_FAILED, operation);
  }
}
