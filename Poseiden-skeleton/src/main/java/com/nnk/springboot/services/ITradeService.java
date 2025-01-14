package com.nnk.springboot.services;

import java.util.Optional;

import com.nnk.springboot.domain.Trade;

public interface ITradeService {
    public Iterable<Trade> getTrades();

    public Optional<Trade> getTradeById(Integer id);

    public Trade saveTrade(Trade trade);

    public void deleteTradeById(Integer id);
}
