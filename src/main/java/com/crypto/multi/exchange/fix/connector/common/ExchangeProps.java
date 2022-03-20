package com.crypto.multi.exchange.fix.connector.common;

public class ExchangeProps {

    public enum Name {
        COINBASE,
        FTX,
        BITSTAMP,
    }

    private Name name;
    private String config;
    private String secret;
    private String userName;
    private String password;
    private String passphrase;

    public Name getName() {
        return name;
    }
    public void setName(Name name) {
        this.name = name;
    }

    public String getConfig() {
        return config;
    }
    public void setConfig(String config) {
        this.config = config;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassphrase() {
        return passphrase;
    }

    public void setPassphrase(String passphrase) {
        this.passphrase = passphrase;
    }

}
