package org.example.shoppingmall_miniproject.exception;

public class NoEnoughStockException extends RuntimeException {
    public NoEnoughStockException() {
        super();
    }

    public NoEnoughStockException(String message) {
        super(message);
    }


}
