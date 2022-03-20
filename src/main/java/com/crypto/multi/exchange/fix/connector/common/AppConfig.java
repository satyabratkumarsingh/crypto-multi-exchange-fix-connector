package com.crypto.multi.exchange.fix.connector.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AppConfig {

    List<ExchangeProps> exchanges;

    public List<ExchangeProps> getExchanges() {
        return exchanges;
    }

    public void setExchanges(List<ExchangeProps> exchanges) {
        this.exchanges = exchanges;
    }
}
