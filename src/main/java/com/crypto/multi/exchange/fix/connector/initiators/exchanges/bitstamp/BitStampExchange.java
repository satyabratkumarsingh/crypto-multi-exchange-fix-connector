package com.crypto.multi.exchange.fix.connector.initiators.exchanges.bitstamp;
import com.crypto.multi.exchange.fix.connector.common.ExchangeProps;
import com.crypto.multi.exchange.fix.connector.initiators.exchanges.IExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import quickfix.*;

import java.io.IOException;
import java.io.InputStream;

public class BitStampExchange implements IExchange {
    static SocketInitiator socketInitiator;
    private static final Logger logger = LoggerFactory.getLogger(BitStampExchange.class);
    @Override
    public void run(ExchangeProps exchangeProps) {
        logger.info("========  Starting connection from BitStamp exchange ==============");
        InputStream inputStream = null;
        try {
            inputStream = BitStampExchange.class.getClassLoader().getResourceAsStream(exchangeProps.getConfig());
            if (inputStream == null) {
                throw new IllegalArgumentException("FIX connection file not found !!");
            }
            logger.info("========  Loaded BitStamp  Config File  ==============");
            SessionSettings settings = new SessionSettings(inputStream);
            Application application = new BitStampFixApp(exchangeProps);
            FileStoreFactory storeFactory = new FileStoreFactory(settings);
            SLF4JLogFactory logFactory = new SLF4JLogFactory(settings);
            socketInitiator = new SocketInitiator(application, storeFactory, settings,
                    logFactory, new DefaultMessageFactory());
            socketInitiator.start();
            while (!socketInitiator.isLoggedOn()) {
                logger.info("Waiting for logged on to Bit Stamp...");
                Thread.sleep(1000);
            }
            logger.info("The status of the logged in App is " + socketInitiator.isLoggedOn());
            SessionID sessionId = socketInitiator.getSessions().get(0);
            logger.info("Got the session for Bit Stamp " + sessionId);
            do {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    logger.info("Error in starting connection for Bitstamp");
                }
            } while (true);
        } catch (Exception e) {
            logger.error("Error in starting connection : " + e.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (socketInitiator != null) {
                    socketInitiator.stop();
                }
            } catch (IOException ex) {
                logger.error("Error in stopping  : " + ex.getMessage());
            }
        }
    }
}
