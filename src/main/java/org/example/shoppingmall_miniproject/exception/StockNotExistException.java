package org.example.shoppingmall_miniproject.exception;

public class StockNotExistException extends RuntimeException {
    public StockNotExistException() {
        super();
    }

    public StockNotExistException(String message) {
        super(message);
    }
}
