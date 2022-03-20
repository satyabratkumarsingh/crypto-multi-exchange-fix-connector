package com.crypto.multi.exchange.fix.connector.initiators.exchanges;

import com.crypto.multi.exchange.fix.connector.common.ExchangeProps;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public interface IExchange {

    void run(ExchangeProps exchangeProps);

    default void runInSeparateThread(ExchangeProps exchangeProps){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Runnable runnable = ()-> run(exchangeProps);
        executor.submit(runnable);
    }

}
