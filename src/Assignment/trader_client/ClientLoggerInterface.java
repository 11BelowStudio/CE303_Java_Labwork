package Assignment.trader_client;

import Assignment.shared.GiveStockResponse;
import Assignment.shared.LoggableMarketInfo;
import Assignment.shared.ResponseInterface;

import java.util.Set;

public interface ClientLoggerInterface {


    /**
     * Logs the ID of this client
     * @param userID the id of this client
     */
    default void logYourId(String userID){
        System.out.println("Your ID: " + userID);
    }

    /**
     * Logs information about a response received from the server.
     * Default implementation prints the success, response body, and marketInfo stuff to console.
     * @param theResponse the response that's being output.
     */
    default void logResponse(ResponseInterface theResponse){
        StringBuilder sb = new StringBuilder();
        if (theResponse.getSuccess()){
            sb.append("Request succeeded. ");
        } else{
            sb.append("Request failed. ");
        }
        sb.append(theResponse.getInfo());
        System.out.println(sb.toString());
    }

    /**
     * Logs information about a GiveStockResponse received from the server.
     * Default implementation is the default logResponse method
     * @param theResponse the GiveStockResponse to log
     */
    default void logGiveResponse(ResponseInterface theResponse){
        this.logResponse(theResponse);
    }

    /**
     * This is responsible for logging whether or not you currently own the stock
     * <p>
     * Default implementation prints 'You own the stock'/'You do not own the stock' to console.
     * @param isMine whether or not you own the stock.
     */
    default void logWhetherOrNotYouOwnTheStock(boolean isMine){
        if (isMine){
            System.out.println("You own the stock");
        } else{
            System.out.println("You do not own the stock");
        }
    }

    /**
     * This method will be used to output details of the market info.
     * <p>
     * Default implementation prints toString method of marketInfo to the console.
     * @param info the LoggableMarketInfo object that contains the current state of the market
     */
    default void logMarketInfo(LoggableMarketInfo info){
        System.out.println("\nMARKET INFO");
        System.out.println(info.toString());
        System.out.println("\n");


        /*
        Set<String> traders = info.getTraders();
        if (traders.isEmpty()){
            System.out.println("ERROR: INVALID MARKET INFO");
            System.out.println("ERROR: INVALID MARKET INFO");
        } else{
            String stockholder = info.getStockholder();
            boolean notShownStockholderYet = true;
            System.out.println("Traders (" + traders.size() + " connected)");
            for (String s: traders) {
                if (notShownStockholderYet && s.equals(stockholder)){
                    notShownStockholderYet = false;
                    System.out.println(s + " owns the stock");
                } else{
                    System.out.println(s);
                }
            }
            System.out.println("\nStockholder: " + stockholder);
        }
        System.out.println("\n");
        */
    }


    @Deprecated
    static String formatLoggableMarketInfo(LoggableMarketInfo info){
        StringBuilder sb = new StringBuilder();

        Set<String> traders = info.getTraders();
        if (traders.isEmpty()){
            sb.append("ERROR: INVALID MARKET INFO");
            sb.append("ERROR: INVALID MARKET INFO");
        } else{
            String stockholder = info.getStockholder();
            boolean notShownStockholderYet = true;
            sb.append("Traders (").append(traders.size()).append(" connected)");
            for (String s: traders) {
                if (notShownStockholderYet && s.equals(stockholder)){
                    notShownStockholderYet = false;
                    sb.append(s).append(" owns the stock");
                } else{
                    sb.append(s);
                }
            }
            sb.append("\nStockholder: ").append(stockholder);
        }

        return sb.toString();
    }

}
