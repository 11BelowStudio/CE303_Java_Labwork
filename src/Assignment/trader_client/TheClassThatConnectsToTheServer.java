package Assignment.trader_client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import Assignment.shared.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TheClassThatConnectsToTheServer implements AutoCloseable {

    //final int port = 8888;
    private final Scanner reader;
    private final PrintWriter writer;

    private final Gson clientGson;


    /**
     * Constructor
     * @param hostName the hostname for the connection
     * @throws IOException if something goes bad
     */
    TheClassThatConnectsToTheServer(String hostName, int port) throws IOException {

        Socket theSocket = new Socket(hostName, port);

        reader = new Scanner(theSocket.getInputStream());

        writer = new PrintWriter(theSocket.getOutputStream(), true);

        GsonBuilder theBuilder = new GsonBuilder();
        clientGson = theBuilder.create();

    }

    /**
     * This sends the connectToServer request
     * @return the response from that request
     */
    ResponseInterface connectToServer() {
        writer.println(clientGson.toJson(new Request(RequestInterface.CONNECTION_REQUEST)));
        return clientGson.fromJson(reader.nextLine(), ConnectResponse.class);
    }

    /**
     * This sends a getMarketInfo request
     * @return the response from that request
     */
    ResponseInterface getMarketInfo() {
        writer.println(clientGson.toJson(new Request(RequestInterface.INFO_REQUEST )));

        return clientGson.fromJson(reader.nextLine(), GenericResponse.class);
    }

    /**
     * This sends a giveStock request
     * @param recipient the individual who this client is trying to give their stock to
     * @return the response from that request
     */
    ResponseInterface giveStock(String recipient) {
        writer.println(clientGson.toJson(new Request(RequestInterface.TRADE_REQUEST, recipient)));

        return clientGson.fromJson(reader.nextLine(), GiveStockResponse.class);
    }

    /**
     * Sends the disconnect request to the server
     * @return the response from the server to that request
     */
    ResponseInterface disconnect() {
        writer.println(clientGson.toJson(new Request(RequestInterface.DISCONNECT_REQUEST)));

        return clientGson.fromJson(reader.nextLine(),GenericResponse.class);
    }


    @Override
    public void close() throws Exception {
        reader.close();
        writer.close();
    }
}
