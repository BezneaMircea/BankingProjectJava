package org.poo.bank;

import lombok.Data;
import org.poo.fileio.ExchangeInput;

@Data
public final class ExchangeRate {
    private String from;
    private String to;
    private double rate;
    private int timestamp;

    public ExchangeRate(ExchangeInput input) {
        from = input.getFrom();
        to = input.getTo();
        rate = input.getRate();
        timestamp = input.getTimestamp();
    }
}
