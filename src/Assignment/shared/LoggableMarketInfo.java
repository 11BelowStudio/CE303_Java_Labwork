package Assignment.shared;

import java.util.HashSet;
import java.util.Set;

/**
 * A somewhat more lightweight superclass for MarketInfo.
 * Originally this was an interface, but gson didn't like deserializing it.
 * So it's an abstract superclass with the bare minimum of functionality instead.
 */
public abstract class LoggableMarketInfo {

    /**
     * The set of all the Traders in the market
     */
    final Set<String> traders;
    /**
     * The ID of the trader who is the stockholder
     */
    String stockholder;

    public LoggableMarketInfo(){
        traders = new HashSet<>();
        stockholder = "";
    }

    public LoggableMarketInfo(Set<String> currentTraders, String currentStockholder){
        traders = new HashSet<>();
        traders.addAll(currentTraders);
        stockholder = currentStockholder;
    }


    /**
     * Obtain the set of traders in the market
     * @return the set of traders
     */
    public Set<String> getTraders(){
        return traders;
    }

    /**
     * Obtain the identifier of the trader who has the stock
     * @return the identifier of the trader with the stock
     */
    public String getStockholder(){
        return stockholder;
    }

    /**
     * Builds a string showing all the traders connected to the market,
     * and who the stockholder is
     * @return a string showing all the details of this MarketInfo
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        if (traders.isEmpty()){
            sb.append("No traders in market!");
            sb.append("Server owns the stock.");
        } else{
            boolean notShownStockholderYet = true;
            sb.append("Traders (").append(traders.size()).append(" connected)\n");
            for (String s: traders) {
                if (notShownStockholderYet && s.equals(stockholder)){
                    notShownStockholderYet = false;
                    sb.append(s).append(" owns the stock");
                } else{
                    sb.append(s);
                }
                sb.append("\n");
            }
            sb.append("\nStockholder: ").append(stockholder);
        }
        return sb.toString();
    }
}
