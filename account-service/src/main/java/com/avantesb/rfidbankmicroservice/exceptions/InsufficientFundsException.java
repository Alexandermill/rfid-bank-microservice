package com.avantesb.rfidbankmicroservice.exceptions;

public class InsufficientFundsException extends SimpleBankingGlobalException{
    public InsufficientFundsException(String message, String code){
        super(message, code);
    }
}
