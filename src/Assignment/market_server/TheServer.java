package Assignment.market_server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TheServer {


    private static int port = 8888;

    private static Market theMarket;

    private static ServerLogger logger;


    /**
     * This is the main method for the serverside bites.
     * @param args command line arguments. 1st argument can be an integer, which can be used as a port number.
     *             If there are no command line arguments, or the 1st command line argument isn't a valid number, the port is set to 8888.
     */
    public static void main(String[] args){
		
		logger = new ServerLogger();


		if (args.length > 0){
			try{
				port = Integer.parseInt(args[0]);
			} catch (ClassCastException e) {
				// >:( angery
				logger.logError(args[0] + " isn't a port number >:( so imma connect to port 8888 anyway");
			}
		}


		
        
        theMarket = new Market(logger);


        /*
        //some (now commented-out) sample queries to send

        Gson sampleGson = new GsonBuilder().create();
        System.out.println(sampleGson.toJson(new Request(Request.CONNECTION_REQUEST,"")));
        System.out.println(sampleGson.toJson(new Request(Request.INFO_REQUEST,"")));
        System.out.println(sampleGson.toJson(new Request(Request.TRADE_REQUEST,"deez nutz")));
        System.out.println(sampleGson.toJson(new Request(Request.DISCONNECT_REQUEST,"")));
        */

        runTheServer();


    }

    private static void runTheServer(){
        ServerSocket theServerSocket;
        try{
            theServerSocket = new ServerSocket(port);
            logger.logInfo("Waiting for incoming connections...");
            while (true){
                Socket traderSocket = theServerSocket.accept();
                TraderHandler newTrader = new TraderHandler(traderSocket, theMarket, logger);
                new Thread(newTrader).start();
            }
        } catch (IOException e){
            logger.logError("error!\n" + e.getMessage());
        }
    }

}
