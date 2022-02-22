# crypto-multi-exchange-fix-connector


A JAVA library which lets you send FIX orders on multiple exchanges (Coinbase, Bitstamp, FTX etc.. )

It runs one acceptor (& multiple initiators) which accepts a Crypto booking order, and then based on exchange specified, redirects to corresponding exchange.

Uses LMAX Disruptor for performance reasons.
