package org.example.shoppingmall_miniproject.exception;

public class OrderCancelException extends RuntimeException {
    public OrderCancelException() {
        super();
    }

    public OrderCancelException(String message) {
        super(message);
    }
}
