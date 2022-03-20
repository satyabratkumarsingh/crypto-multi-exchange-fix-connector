package com.crypto.multi.exchange.fix.connector.common;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface IFixProcess {
    void start() throws IOException, ExecutionException, InterruptedException;
}
