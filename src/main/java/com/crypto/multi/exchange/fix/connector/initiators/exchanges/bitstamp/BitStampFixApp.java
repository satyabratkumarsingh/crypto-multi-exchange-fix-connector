package com.crypto.multi.exchange.fix.connector.initiators.exchanges.bitstamp;

import com.crypto.multi.exchange.fix.connector.common.ExchangeProps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import quickfix.*;
import quickfix.field.MsgType;

public class BitStampFixApp extends MessageCracker implements Application {

    private static Logger logger = LoggerFactory.getLogger(BitStampFixApp.class);

    private final ExchangeProps  exchangeProps;
    public BitStampFixApp(ExchangeProps  exchangeProps) {
        this.exchangeProps = exchangeProps;
    }

    @Override
    public void onCreate(SessionID sessionID) {

    }
    @Override
    public void onLogon(SessionID sessionID) {

        logger.info("Sending logon message......");
    }

    @Override
    public void onLogout(SessionID sessionID) {

    }
    @Override
    public void toAdmin(Message message, SessionID sessionID) {
        final String msgType;
        try {
            msgType = message.getHeader().getString(MsgType.FIELD);
            if (MsgType.LOGON.compareTo(msgType) == 0) {
                message.setString(553, this.exchangeProps.getUserName());
                message.setString(554, this.exchangeProps.getPassword());
            }
        } catch (FieldNotFound fieldNotFound) {
            fieldNotFound.printStackTrace();
        }
    }

    @Override
    public void fromAdmin(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        logger.info("================================");
        logger.info(message.toRawString());
        logger.info("================================");
    }

    @Override
    public void toApp(Message message, SessionID sessionID) throws DoNotSend {

    }

    @Override
    public void fromApp(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        logger.info("================================");
        logger.info(message.toRawString());
        logger.info("================================");
    }

}