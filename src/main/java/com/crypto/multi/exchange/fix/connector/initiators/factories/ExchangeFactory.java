package com.crypto.multi.exchange.fix.connector.initiators.factories;

import com.crypto.multi.exchange.fix.connector.common.ExchangeProps;
import com.crypto.multi.exchange.fix.connector.initiators.exchanges.IExchange;
import com.crypto.multi.exchange.fix.connector.initiators.exchanges.bitstamp.BitStampExchange;
import com.crypto.multi.exchange.fix.connector.initiators.exchanges.coinbase.CoinBaseExchange;

public class ExchangeFactory {

    public IExchange createExchange(ExchangeProps.Name name) {
        if(name == ExchangeProps.Name.COINBASE) {
            return new CoinBaseExchange();
        }
        if(name == ExchangeProps.Name.BITSTAMP) {
            return new BitStampExchange();
        }
        throw new IllegalArgumentException("Not a valid exchange !!!!!!");
    }
}
