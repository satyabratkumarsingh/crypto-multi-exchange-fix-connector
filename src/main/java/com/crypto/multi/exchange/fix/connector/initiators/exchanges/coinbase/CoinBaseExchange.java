package com.crypto.multi.exchange.fix.connector.initiators.exchanges.coinbase;

import com.crypto.multi.exchange.fix.connector.common.ExchangeProps;
import com.crypto.multi.exchange.fix.connector.initiators.exchanges.IExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import quickfix.*;

import java.io.IOException;
import java.io.InputStream;

public class CoinBaseExchange implements IExchange {

    static SocketInitiator socketInitiator;
    private static Logger logger = LoggerFactory.getLogger(CoinBaseExchange.class);

    @Override
    public void run(ExchangeProps  exchangeProps) {
        logger.info("========  Starting connection from Coinbase exchange ==============");
        InputStream inputStream = null;
        try {
            inputStream = CoinBaseExchange.class.getClassLoader().getResourceAsStream(exchangeProps.getConfig());
            if (inputStream == null) {
                throw new IllegalArgumentException("FIX connection file not found for Coinbase !!");
            }
            SessionSettings settings = new SessionSettings(inputStream);
            Application application = new CoinBaseFixApp(exchangeProps);
            FileStoreFactory storeFactory = new FileStoreFactory(settings);
            SLF4JLogFactory logFactory = new SLF4JLogFactory(settings);
            socketInitiator = new SocketInitiator(application, storeFactory, settings,
                    logFactory, new DefaultMessageFactory());
            socketInitiator.start();
            while (!socketInitiator.isLoggedOn()) {
                logger.info("Waiting for logged on to Exchange...");
                Thread.sleep(1000);
            }
            logger.info("The status of the logged in App is " + socketInitiator.isLoggedOn());
            SessionID sessionId = socketInitiator.getSessions().get(0);
            logger.info("Got the session for Coinbase " + sessionId);
            do {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    logger.info("Error in starting connection for Exchange");
                }
            } while (true);
        } catch (Exception e) {
            logger.error("Error in starting connection : " +  e.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                // ignore on close
            }
        }
    }

}
