package com.crypto.multi.exchange.fix.connector.initiators;

import com.crypto.multi.exchange.fix.connector.common.AppConfig;
import com.crypto.multi.exchange.fix.connector.common.ExchangeProps;
import com.crypto.multi.exchange.fix.connector.common.IFixProcess;
import com.crypto.multi.exchange.fix.connector.initiators.exchanges.IExchange;
import com.crypto.multi.exchange.fix.connector.initiators.factories.ExchangeFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;

public class MultiExchangeInitiator implements IFixProcess {

    private static Logger logger = LoggerFactory.getLogger(MultiExchangeInitiator.class);
    @Override
    public void start() throws IOException {
        logger.info("=========== Starting the initiators ==================");
        try {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File file = new File(classLoader.getResource("config.yaml").getFile());
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        AppConfig config = mapper.readValue(file, AppConfig.class);
        ExchangeFactory exchangeFactory = new ExchangeFactory();
        for(ExchangeProps exchangeProps: config.getExchanges()) {
            IExchange exchangeApp = exchangeFactory.createExchange(exchangeProps.getName());
            exchangeApp.runInSeparateThread(exchangeProps);
        }
        } catch (IOException ex) {
            logger.error("Error while reading config file, please specify a valid config file!!", ex);
            throw ex;
        }
        catch (Exception ex) {
            logger.error("Error while starting one of the exchanges", ex);
            throw ex;
        }
    }
}
