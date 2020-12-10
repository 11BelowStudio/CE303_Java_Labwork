package Assignment.shared;

import java.util.Set;

/**
 * This holds Info about the Market
 */
public class MarketInfo extends LoggableMarketInfo {


    public MarketInfo(){
        super();
    }
    
    public MarketInfo(MarketInfo info){
        super(info.getTraders(), info.getStockholder());
    }

    public MarketInfo(Set<String> currentTraders, String currentStockholder){
        super(currentTraders,currentStockholder);
    }

    public MarketInfo(LoggableMarketInfo lMarket){
        this(lMarket.getTraders(),lMarket.getStockholder());
    }

    public void updateTraders(Set<String> currentTraders){
        traders.clear();
        traders.addAll(currentTraders);
    }

    public void updateStockholder(String currentStockholder){
        stockholder = currentStockholder;
    }

    public void updateMarketInfo(Set<String> currentTraders, String currentStockholder){
        traders.clear();
        traders.addAll(currentTraders);
        stockholder = currentStockholder;
    }

    public MarketInfo copyThis(){
        return new MarketInfo(traders,stockholder);
    }

}
