package Assignment.trader_client;

import Assignment.shared.*;

import java.util.Scanner;

/**
 * This is a client which is 100% console-based. 
 */
public class ConsoleClient extends GenericClient {




    private final Scanner userInput;

    private static final int SEND_INFO = 0;
    private static final int SEND_TRADE = 1;
    private static final int SEND_QUIT = 2;

    public ConsoleClient(String hostname, int port) throws Exception{
        super(hostname, port);

        log = new ClientLoggerInterface() {};

        log.logYourId(id);
        userInput = new Scanner(System.in);
    }

    public void runTheClient() throws Exception{
        ResponseInterface theResponse = new GenericResponse(false);
        int requestToSend;
        while (connected){
            /*
             * Prints user ID, current state of the market, works out whether or not you own the stock,
             * and then prints something telling you if you own the stock or not.
             * followed by a newline (and then options)
             */
            log.logYourId(id);
            log.logMarketInfo(currentMarket);
            youHaveTheStock = (id.equals(currentMarket.getStockholder()));
            log.logWhetherOrNotYouOwnTheStock(youHaveTheStock);
            if (youHaveTheStock){
                requestToSend = stockholderOptions();
            } else{
                requestToSend = proletariatOptions();
            }

            switch (requestToSend){
                case SEND_INFO:
                    theResponse = theClient.getMarketInfo();
                    break;
                case SEND_TRADE:
                    System.out.println("Please enter the ID of the trader you want to give the stock to");
                    String sendTo = userInput.nextLine();
                    theResponse = theClient.giveStock(sendTo);
                    break;
                case SEND_QUIT:
                    System.out.println("aight bye");
                    connected = false;
                    break;
            }

            System.out.println();

            //if still connected, output the results
            if (connected) {
                log.logResponse(theResponse);
                currentMarket = theResponse.getMarketInfo();
            }

        }
        //if user wanted to disconnect, send the disconnect request, and close the client.
        theClient.disconnect();
        theClient.close();
    }

    /**
     * Options for the stockholder
     * @return an int representing a specific option
     */
    private int stockholderOptions(){
        System.out.println("You have the stock. Please select an option:");
        System.out.println("a: Get updated market info");
        System.out.println("b: Give stock to someone else");
        System.out.println("c: Disconnect");
        System.out.println("\n");
        boolean waitingForInput = true;
        char input = 'a';
        do{
            try{
                System.out.println("Please enter the first character of the option you want to choose:");
                input = userInput.nextLine().toLowerCase().charAt(0);
                waitingForInput = false;
            } catch (Exception e){
                System.out.println("please ensure you're actually inputting some data.");
            }
        } while (waitingForInput);
        switch (input){
            case 'a':
                return SEND_INFO;
            case 'b':
                return SEND_TRADE;
            case 'c':
                return SEND_QUIT;
            default:
                System.out.println("idk what you're trying to do, so I'm just updating the market info instead.");
                return SEND_INFO;
        }
    }

    /**
     * Options for the common person, who does not own a stock.
     * @return an int representing a specific option
     */
    private int proletariatOptions(){
        while (true) {
            System.out.println("Please select an option:");
            System.out.println("a: Wait for market info to update");
            System.out.println("b: Disconnect");
            System.out.println("\n");
            boolean waitingForInput = true;
            char input = 'a';
            do {
                try {
                    System.out.println("Please enter the first character of the option you want to choose:");
                    input = userInput.nextLine().toLowerCase().charAt(0);
                    waitingForInput = false;
                } catch (Exception e) {
                    System.out.println("please ensure you're actually inputting some data.");
                }
            } while (waitingForInput);
            switch (input) {
                case 'a':
                    System.out.println("Please wait for the market info to update");
                    return SEND_INFO;
                case 'b':
                    return SEND_QUIT;
                default:
                    System.out.println("idk what you're trying to do. pls enter 'a' or 'b'");
                    break;
            }
        }
    }





    /**
     * The main method for the ConsoleClient
     * @param args the command line arguments.
     *             If you want to connect to something that isn't 'localhost', put that as the first word in the command line arguments.
     *             If you want to use a port that isn't 8888, pass that as the second word in the command line arguments.
     * @throws Exception because connections do be like that sometimes
     */
    public static void main(String[] args) throws Exception{
        String host = "localhost";
        int port = 8888;
        if (args.length > 0){
            host = args[0];
            if (args.length > 1){
                try{
                    port = Integer.parseInt(args[1]);
                } catch (ClassCastException e) {
                    // >:( angery
                    System.out.println(args[1] + " isn't a port number >:( so imma connect to port 8888 anyway");
                }
            }
        }
        ConsoleClient client = new ConsoleClient(host, port);
        client.runTheClient();
    }
}
