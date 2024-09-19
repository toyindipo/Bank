package com.dipo.bank.dto;

import java.math.BigDecimal;

public class AccountPosting {
    private BigDecimal amount;
    private Long userId;

    public AccountPosting() {
    }

    public AccountPosting(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
