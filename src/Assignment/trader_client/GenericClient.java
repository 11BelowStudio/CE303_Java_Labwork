package Assignment.trader_client;

import Assignment.shared.LoggableMarketInfo;
import Assignment.shared.MarketInfo;
import Assignment.shared.ResponseInterface;

public abstract class GenericClient {

    TheClassThatConnectsToTheServer theClient;

    String id;
    LoggableMarketInfo currentMarket;

    boolean youHaveTheStock;

    boolean connected;

    ClientLoggerInterface log;


    GenericClient(String hostname, int port) throws Exception{
        theClient = new TheClassThatConnectsToTheServer("localhost", port);

        youHaveTheStock = false;

        System.out.println("sending connect request");

        ResponseInterface connection = theClient.connectToServer();
        System.out.println("got response");
        if (connection.getSuccess()) {
            id = connection.getInfo();
            currentMarket = new MarketInfo(connection.getMarketInfo());
            System.out.println("Connected successfully.");
            connected = true;
        } else {
            System.out.println("Connection unsuccessful. Aborting.");
            throw new Exception();
        }
    }



}
