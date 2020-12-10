package Assignment.market_server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import Assignment.shared.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class TraderHandler implements Runnable {

    private final Socket theSocket;

    private final Market theMarket;

    /**
     * BEHOLD! MY SOLUTION TO THE PROBLEM OF ENSURING CLIENTS HAVE UNIQUE IDENTIFIERS THAT CAN'T BE RE-USED
     *
     * due to how UUIDs work and how they're generated and how there's a stupidly large number of them,
     * there's a negligible risk of two traders getting the same UUIDs as each other.
     *
     * Anywho, if there somehow were enough traders joining so that two traders had the same UUID,
     * chances are that this collision will be the least of your concerns.
     */
    private final String id;

    private final Gson traderHandlerGson;

    private final ServerLoggerInterface theLogger;

    private static CyclicBarrier clientBarrier = new CyclicBarrier(1);

    private static final Object barrierSyncObject = new Object();

    private static int clientCount = 0;

    private static void changeClientCount(boolean increment){
        synchronized (barrierSyncObject) {
            if (increment) {
                clientCount++;
            } else{
                clientCount--;
                if (clientCount < 0){ clientCount = 0; }
            }
            clientBarrier.reset();
            if (clientCount > 0) {
                clientBarrier = new CyclicBarrier(clientCount);
            }
        }
    }

    private static void manuallyBreakBarrier(){
        synchronized (barrierSyncObject){
            clientBarrier.reset();
            clientBarrier = new CyclicBarrier(clientCount);
        }
    }


    TraderHandler(Socket socket, Market market, ServerLoggerInterface logger){
        theSocket = socket;
        theMarket = market;
        theLogger = logger;

        //HAHA RFC 4122 GO BRR
        id = UUID.randomUUID().toString().toLowerCase();


        GsonBuilder gsonBuilder = new GsonBuilder();
        traderHandlerGson = gsonBuilder.create();
    }


    String getId(){
        return id;
    }

    @Override
    public void run() {
        //add this client to the clientCount (if they didn't connect successfully, they'll quickly get disconnected and decremented from the clientCount anyway)
        changeClientCount(true);
        try{
            Scanner reader = new Scanner(theSocket.getInputStream());
            PrintWriter writer = new PrintWriter(theSocket.getOutputStream(),true);
            try{
                //will wait for the login request
                RequestInterface clientRequest= traderHandlerGson.fromJson(reader.nextLine(),Request.class);


                boolean connected = false;

                String responseString = "";

                if (clientRequest.getRequestCode() == RequestInterface.CONNECTION_REQUEST){
                    //if it's a login request, it's trader time.
                    connected = true;
                    theMarket.addNewTrader(this);
                    responseString = traderHandlerGson.toJson(new ConnectResponse(true, theMarket.getCurrentMarketInfo(), id));
                    writer.println(responseString);
                }


                while (connected){

                    try {

                        boolean hasTheStock = theMarket.getCurrentMarketInfo().getStockholder().equals(id);
                        boolean waitForMarketUpdate = false;

                        //obtains the next request from the client
                        clientRequest = traderHandlerGson.fromJson(reader.nextLine(), Request.class);

                        switch (clientRequest.getRequestCode()) {
                            //if client requested info
                            case RequestInterface.INFO_REQUEST:
                                if (hasTheStock) {
                                    //if they have the stock, things won't change for them, so they'll get the current market info right now
                                    responseString = traderHandlerGson.toJson(new GenericResponse( true, (theMarket.getCurrentMarketInfo())));
                                    manuallyBreakBarrier(); //also manually breaks the barrier, so everyone who is waiting will have that updated
                                } else {
                                    //if they don't have the stock, they'll need to wait for the market to update for them to get updated market info
                                    waitForMarketUpdate = true;
                                }
                                break;
                                //if they requested to trade
                            case RequestInterface.TRADE_REQUEST:
                                //attempts to perform the trade request (will fail if the trade isn't legit)
                                responseString = traderHandlerGson.toJson(theMarket.giveStock(this, clientRequest.getRequestBody()));
                                if (hasTheStock){
                                    //if this trader has/had the stock at the start of this loop, it will manually break the barrier
                                    //(so everyone waiting at the clientBarrier will not need to wait any longer)
                                    manuallyBreakBarrier();
                                }
                                break;
                            case Request.DISCONNECT_REQUEST:
                                //if the user requested a disconnect, they'll get disconnected (no more loops).
                                connected = false;
                                break;
                            default:
                                //if it was something else, give them a generic 'I have no idea what you're trying to do' response.
                                responseString = traderHandlerGson.toJson(new GenericResponse(false,theMarket.getCurrentMarketInfo()));
                                break;
                        }
                        if (waitForMarketUpdate) {
                            //if waiting for market update, wait at the clientBarrier for up to 1 minute
                            clientBarrier.await(1, TimeUnit.MINUTES);
                            //and then, after everyone reaches the clientBarrier (or after a minute), obtain the current market info, use that as the responseString
                            responseString = traderHandlerGson.toJson(new GenericResponse( true, (theMarket.getCurrentMarketInfo())));
                        }
                        //give user the responseString
                        writer.println(responseString);
                    } catch (JsonSyntaxException e){
                        //if the json in the request was bad, respond with a generic 'no'.
                        writer.println(traderHandlerGson.toJson(new GenericResponse(false)));
                    } catch (BrokenBarrierException be){
                        //if trader was waiting at the barrier, and the barrier was broken (either by a connect, disconnect, or a trade)
                        //give trader the market info they were waiting for
                        responseString = traderHandlerGson.toJson(new GenericResponse( true, (theMarket.getCurrentMarketInfo())));
                        writer.println(responseString);
                    }
                }
            } catch (Exception ignored){
            } finally {
                //if a trader disconnected (or a big exception happened), handle the disconnect
                theMarket.removeTrader(this);
                writer.write(traderHandlerGson.toJson(new DisconnectResponse()));
                reader.close();
                writer.close();
                theSocket.close();
                //theLogger.logDisconnect(id);

            }
        } catch (IOException e){
            //e.printStackTrace();
            theLogger.logError(e.getMessage());
        }
        //remove this client from the client count
        changeClientCount(false);
    }
}
