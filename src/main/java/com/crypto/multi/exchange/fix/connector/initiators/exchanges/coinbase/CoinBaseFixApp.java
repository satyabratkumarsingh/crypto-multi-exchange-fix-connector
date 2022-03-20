package com.crypto.multi.exchange.fix.connector.initiators.exchanges.coinbase;

import com.crypto.multi.exchange.fix.connector.common.ExchangeProps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import quickfix.*;
import quickfix.field.MsgType;
import quickfix.field.Password;
import quickfix.fix42.Logon;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class CoinBaseFixApp extends MessageCracker implements Application {

    private static final char SOH = '\u0001';
    private static Logger logger = LoggerFactory.getLogger(CoinBaseFixApp.class);

    private final ExchangeProps exchangeProps;
    public CoinBaseFixApp(ExchangeProps exchangeProps) {
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
                message.setInt(108, 30);
                message.setInt(98, 0);
                message.setString(Password.FIELD, this.exchangeProps.getPassphrase());
                StringBuilder presign = new StringBuilder();
                presign.append(message.getHeader().getString(52));
                presign.append(SOH);
                presign.append(Logon.MSGTYPE);
                presign.append(SOH);
                presign.append(1);
                presign.append(SOH);
                presign.append(sessionID.getSenderCompID());
                presign.append(SOH);
                presign.append(sessionID.getTargetCompID());
                presign.append(SOH);
                presign.append(this.exchangeProps.getPassphrase());
                try {
                    SecretKeySpec key = new SecretKeySpec(Base64.getDecoder().decode(this.exchangeProps.getSecret()), "HmacSHA256");
                    Mac mac = Mac.getInstance("HmacSHA256");
                    mac.init(key);
                    mac.update(presign.toString().getBytes(StandardCharsets.US_ASCII));
                    String sign = Base64.getEncoder().encodeToString(mac.doFinal());
                    message.setString(96, sign);
                } catch (NoSuchAlgorithmException | InvalidKeyException var6) {
                    throw new RuntimeException(var6);
                }
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
