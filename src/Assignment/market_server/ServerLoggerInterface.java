package Assignment.market_server;

import Assignment.shared.LoggableMarketInfo;

import java.util.Set;

public interface ServerLoggerInterface {

    /**
     * This method will be used to record when a trader connects to the market.
     * <p>
     * Default implementation prints message to console saying that the trader with that ID has joined.
     * @param connectedTrader the ID of the trader who has connected
     */
    default void logConnect(String connectedTrader){
        System.out.println("Trader " + connectedTrader + " joined!");
    }

    /**
     * This method will be used to record when a trader leaves the market.
     * <p>
     * Default implementation prints message to console saying that the trader with that ID has left
     * @param disconnectedTrader the ID of the trader who disconnected
     */
    default void logDisconnect(String disconnectedTrader){
        System.out.println("Trader " +disconnectedTrader + " left!");
    }

    /**
     * This method will be used to record when server automatically gives the stock to a trader
     * <p>
     * Default implementation prints message to console saying that the trader with that id has been given a stock
     * @param newStockholder the ID of the trader who now has the stock
     */
    default void logServerGaveStock(String newStockholder){
        System.out.println("Server gave stock to " + newStockholder);
    }

    /**
     * This method will be used to record when a trade occurs.
     * @param sender the trader who sent the stock
     * @param recipient the trader who was given the stock
     */
    default void logTrade(String sender, String recipient){
        System.out.println(sender + " gave stock to " + recipient);
    }

    /**
     * This method will be used to print the state of the market to the console.
     * It prints IDs of all stockholders, as well as indicating which trader has the stock.
     * If there are no traders, it prints an appropriate message.
     * @param info the LoggableMarketInfo object that contains the current state of the market
     */
    default void logMarketInfo(LoggableMarketInfo info){
        System.out.println("\nMARKET INFO:");

        Set<String> traders = info.getTraders();
        if (traders.isEmpty()){
            System.out.println("No traders present in market!");
            System.out.println("Stock owned by server");
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

        System.out.println();
    }

    /**
     * Logs errors to the console
     * @param errorMessage the error message that's being output.
     */
    default void logError(String errorMessage){
        System.out.println(errorMessage);
    }
	
	/**
     * Prints some generic info to the console
     * @param infoToLog the string info to log to the console
     */
    default void logInfo(String infoToLog){
        System.out.println(infoToLog);
    }
	
	
}
